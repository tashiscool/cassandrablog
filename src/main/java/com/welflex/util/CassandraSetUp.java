package com.welflex.util;

import io.cassandra.sdk.StatusMessageModel;
import io.cassandra.sdk.column.ColumnAPI;
import io.cassandra.sdk.columnfamily.ColumnFamilyAPI;
import io.cassandra.sdk.constants.APIConstants;
import io.cassandra.sdk.data.DataAPI;
import io.cassandra.sdk.data.DataBulkModel;
import io.cassandra.sdk.data.DataColumn;
import io.cassandra.sdk.data.DataMapModel;
import io.cassandra.sdk.data.DataRowkey;
import io.cassandra.sdk.keyspace.KeyspaceAPI;

import java.util.ArrayList;
import java.util.List;

public class CassandraSetUp {
    // credentials
	public static String TOKEN = "<Token>";
	public static String ACCOUNTID = "<AccountId>";

	// data 
	public static String KS = "BOOKDORA";

	public static String CF_TAGS = "tags";
	public static String CF_TAGS_COL1 = "books";

	public static String CF_BOOKS = "books";
	public static String CF_BOOKS_TAGS = "tags";
	public static String CF_BOOKS_SAMPLER = "sampler";
	public static String CF_BOOKS_ISBN = "isbn";
	public static String CF_BOOKS_TITLE = "title";

	public static String CF_USERS = "users";
	public static String CF_USERS_COL1 = "books";
	public static String CF_USERS_COL2 = "prefs";
	public static String CF_USERS_COL3 = "non-prefs";

	public static String RK = "06-06-2013";

	@SuppressWarnings( "unchecked" )
	public static void main(String[] args) {	
		try {
			StatusMessageModel sm;

			// Create Keyspace
			KeyspaceAPI keyspaceAPI = new KeyspaceAPI(APIConstants.API_URL, TOKEN, ACCOUNTID);
			sm = keyspaceAPI.createKeyspace(KS);
			
		     // Create ColumnFamilies
			ColumnFamilyAPI columnFamilyAPI = new ColumnFamilyAPI(APIConstants.API_URL, TOKEN,ACCOUNTID);
			sm = columnFamilyAPI.createColumnFamily(KS, CF_BOOKS,	APIConstants.COMPARATOR_UTF8);
			sm = columnFamilyAPI.createColumnFamily(KS, CF_USERS,	APIConstants.COMPARATOR_UTF8);
			sm = columnFamilyAPI.createColumnFamily(KS, CF_TAGS,	APIConstants.COMPARATOR_UTF8);

			// Add cols for all families
			ColumnAPI columnAPI = new ColumnAPI(APIConstants.API_URL, TOKEN, ACCOUNTID);
			sm = columnAPI.upsertColumn(KS, CF_BOOKS, CF_BOOKS_TAGS,APIConstants.COMPARATOR_UTF8, true);
			sm = columnAPI.upsertColumn(KS, CF_BOOKS, CF_BOOKS_TITLE,APIConstants.COMPARATOR_UTF8, true);
			sm = columnAPI.upsertColumn(KS, CF_BOOKS, CF_BOOKS_SAMPLER,APIConstants.COMPARATOR_UTF8, true);
			sm = columnAPI.upsertColumn(KS, CF_BOOKS, CF_BOOKS_ISBN,APIConstants.COMPARATOR_UTF8, true);
			
			sm = columnAPI.upsertColumn(KS, CF_USERS, CF_USERS_COL1,APIConstants.COMPARATOR_UTF8, true);
			sm = columnAPI.upsertColumn(KS, CF_USERS, CF_USERS_COL2,APIConstants.COMPARATOR_UTF8, true);
			sm = columnAPI.upsertColumn(KS, CF_USERS, CF_USERS_COL3,APIConstants.COMPARATOR_UTF8, true);
			
			sm = columnAPI.upsertColumn(KS, CF_TAGS, CF_TAGS_COL1,APIConstants.COMPARATOR_UTF8, true);
			
			
			//Add Bulk Data
			DataAPI dataAPI = new DataAPI(APIConstants.API_URL, TOKEN, ACCOUNTID);

			List<DataColumn> columns = new ArrayList<DataColumn>();

			DataColumn dc = new DataColumn(CF_BOOKS_TAGS, "sex,drugs,rockNroll");
			columns.add(dc);
			dc = new DataColumn(CF_BOOKS_ISBN, "a12345");
			columns.add(dc);
			dc = new DataColumn(CF_BOOKS_TITLE, "Walk Hard - the Dewey Cox Story");
			columns.add(dc);
			dc = new DataColumn(CF_BOOKS_SAMPLER, "Once upon a time there was a dude named tash...");
			columns.add(dc);

			List<DataRowkey> rows = new ArrayList<DataRowkey>();
			DataRowkey row = new DataRowkey(RK, columns);
			rows.add(row);

			DataBulkModel dataBulk = new DataBulkModel(rows);

			sm = dataAPI.postBulkData(KS, CF_BOOKS, dataBulk);
			
			// Get Data
			DataMapModel dm = dataAPI.getData(KS, CF_BOOKS, RK, 0, null);
		
			// Delete Keyspace
			//sm = keyspaceAPI.deleteKeyspace(KS);
		
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}

	}
}