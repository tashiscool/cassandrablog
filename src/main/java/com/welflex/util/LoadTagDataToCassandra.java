package com.welflex.util;

import io.cassandra.sdk.constants.APIConstants;
import io.cassandra.sdk.data.DataAPI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.lacassandra.smooshyfaces.persistence.cassandra.CassandraTagsDAO;

public class LoadTagDataToCassandra {

	static Map<String,String> tagMap = new HashMap<String,String>();
	
	@SuppressWarnings( "unchecked" )
	public static void main(String[] args) {	

		String KS = "BOOKDORA";
		String CF_TAGS = "tags";
//		String CF_TAGS = "com.welflex.util.CassandraSetUp.CF_TAGS";

		//Add Bulk Data
		DataAPI dataAPI = new DataAPI(APIConstants.API_URL, "TB8Xnx6khZ", "865cc5bc-04e5-4938-868b-423e74aa8f25");

		CassandraTagsDAO dao = new CassandraTagsDAO();
		
		dao.setKeySpaceName(KS);
		dao.setDataAPI(dataAPI);
		dao.setColumnFamilyName(CF_TAGS);

		try {
			BufferedReader br = new BufferedReader(new FileReader("c:\\testing\\tags.txt"));
			String l = null;
			while ((l = br.readLine()) != null){
				
				
				String[] attribs = l.split(":");
				String[] tags = attribs[0].split(",");
				
				for (String s : tags){
					dao.saveTag(s, attribs[1]);
				}
				
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}
		
}