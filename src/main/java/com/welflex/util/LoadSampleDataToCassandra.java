package com.welflex.util;

import io.cassandra.sdk.StatusMessageModel;
import io.cassandra.sdk.constants.APIConstants;
import io.cassandra.sdk.data.DataAPI;
import io.cassandra.sdk.data.DataBulkModel;
import io.cassandra.sdk.data.DataColumn;
import io.cassandra.sdk.data.DataMapModel;
import io.cassandra.sdk.data.DataRowkey;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class LoadSampleDataToCassandra {

	@SuppressWarnings( "unchecked" )
	public static void main(String[] args) {
		
		String TOKEN = "TB8Xnx6khZ";
		String ACCOUNTID = "865cc5bc-04e5-4938-868b-423e74aa8f25";

		String KS = "BOOKDORA";
		String CF_BOOKS = "books";
		String CF_BOOKS_TAGS = "tags";
		String CF_BOOKS_SAMPLER = "sampler";
		String CF_BOOKS_ISBN = "isbn";
		String CF_BOOKS_TITLE = "title";

		JSONParser parser = new JSONParser();
		
		//Add Bulk Data
		DataAPI dataAPI = new DataAPI(APIConstants.API_URL, TOKEN, ACCOUNTID);
		String[] tags = new String[]{"sex,drugs,rockNroll,turpentine,indifference","rock,food,sports,mustard","desserts,literature,blah"};
		List<DataColumn> columns = new ArrayList<DataColumn>();

		try {
			Object obj = parser.parse(new FileReader("c:\\testing\\books.json"));
			 
			JSONObject jsonObject = (JSONObject) obj;
			
			JSONArray books = (JSONArray)jsonObject.get("books");
			
			Iterator<JSONObject> it = books.iterator();
			int ct = 0;
			while (it.hasNext()){
				ct++;
				JSONObject o = it.next();
				
				String title =  (String)o.get("title");
				String id =  (String)o.get("id");
				
				DataColumn dc = new DataColumn(CF_BOOKS_TAGS, tags[ct%3]);
				columns.add(dc);
				dc = new DataColumn(CF_BOOKS_ISBN, id);
				columns.add(dc);
				dc = new DataColumn(CF_BOOKS_TITLE, title);
				columns.add(dc);
				dc = new DataColumn(CF_BOOKS_SAMPLER, "Once upon a time, in a galaxy far away...");
				columns.add(dc);

				List<DataRowkey> rows = new ArrayList<DataRowkey>();
				DataRowkey row = new DataRowkey(CF_BOOKS_ISBN, columns);
				rows.add(row);

				DataBulkModel dataBulk = new DataBulkModel(rows);

				StatusMessageModel sm = dataAPI.postBulkData(KS, CF_BOOKS, dataBulk);
				System.out.println(sm.getDetail());
				System.out.println(sm.getDetail());
				System.out.println(sm.getError());


				
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}

	}
}