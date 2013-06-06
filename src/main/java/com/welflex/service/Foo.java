package com.welflex.service;

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

public class Foo {
    // credentials
	private static String TOKEN = "<Token>";
	private static String ACCOUNTID = "<AccountId>";

	// data 
	private static String KS = "AAPL";
	private static String CF = "MarketData";
	private static String COL1 = "Open";
	private static String COL2 = "Close";
	private static String COL3 = "High";
	private static String COL4 = "Low";
	private static String COL5 = "Volume";
	private static String COL6 = "AdjClose";
	private static String RK = "18-05-2012";

	public static void main(String[] args) {	
		try {
			StatusMessageModel sm;

			// Create Keyspace
			KeyspaceAPI keyspaceAPI = new KeyspaceAPI(APIConstants.API_URL, TOKEN, ACCOUNTID);
			sm = keyspaceAPI.createKeyspace(KS);
			System.out.println(sm.getMessage() + " | " + sm.getDetail() + " | "
				+ sm.getError());

		        // Create ColumnFamily
			ColumnFamilyAPI columnFamilyAPI = new ColumnFamilyAPI(APIConstants.API_URL, TOKEN,
					ACCOUNTID);
			sm = columnFamilyAPI.createColumnFamily(KS, CF,
					APIConstants.COMPARATOR_UTF8);
			System.out.println(sm.getMessage() + " | " + sm.getDetail() + " | "
					+ sm.getError());

			 // Add Columns (High, Low, Open, Close, Volume, AdjClose)
			ColumnAPI columnAPI = new ColumnAPI(APIConstants.API_URL, TOKEN, ACCOUNTID);
			sm = columnAPI.upsertColumn(KS, CF, COL1,
					APIConstants.COMPARATOR_UTF8, true);
			System.out.println(sm.getMessage() + " | " + sm.getDetail() + " | "
					+ sm.getError());
			sm = columnAPI.upsertColumn(KS, CF, COL2,
					APIConstants.COMPARATOR_UTF8, true);
			System.out.println(sm.getMessage() + " | " + sm.getDetail() + " | "
					+ sm.getError());
			sm = columnAPI.upsertColumn(KS, CF, COL3,
					APIConstants.COMPARATOR_UTF8, true);
			System.out.println(sm.getMessage() + " | " + sm.getDetail() + " | "
					+ sm.getError());
			sm = columnAPI.upsertColumn(KS, CF, COL4,
					APIConstants.COMPARATOR_UTF8, true);
			System.out.println(sm.getMessage() + " | " + sm.getDetail() + " | "
					+ sm.getError());
			sm = columnAPI.upsertColumn(KS, CF, COL5,
					APIConstants.COMPARATOR_UTF8, true);
			System.out.println(sm.getMessage() + " | " + sm.getDetail() + " | "
					+ sm.getError());
			sm = columnAPI.upsertColumn(KS, CF, COL6,
					APIConstants.COMPARATOR_UTF8, true);
			System.out.println(sm.getMessage() + " | " + sm.getDetail() + " | "
					+ sm.getError());

			//Add Bulk Data
			DataAPI dataAPI = new DataAPI(APIConstants.API_URL, TOKEN, ACCOUNTID);

			List columns = new ArrayList();
			DataColumn dc = new DataColumn(COL1, "533.96");
			columns.add(dc);
			dc = new DataColumn(COL2, "530.38", 12000);
			columns.add(dc);
			dc = new DataColumn(COL3, "543.41", 12000);
			columns.add(dc);
			dc = new DataColumn(COL4, "522.18", 12000);
			columns.add(dc);
			dc = new DataColumn(COL5, "26125200", 12000);
			columns.add(dc);
			dc = new DataColumn(COL6, "530.12", 12000);
			columns.add(dc);

			List rows = new ArrayList();
			DataRowkey row = new DataRowkey(RK, columns);
			rows.add(row);

			DataBulkModel dataBulk = new DataBulkModel(rows);

			sm = dataAPI.postBulkData(KS, CF, dataBulk);
			System.out.println(sm.getMessage() + " | " + sm.getDetail() + " | "
					+ sm.getError());

			// Get Data
			DataMapModel dm = dataAPI.getData(KS, CF, RK, 0, null);
			System.out.println(dm.toString());			

			// Delete Keyspace
			sm = keyspaceAPI.deleteKeyspace(KS);
			System.out.println(sm.getMessage() + " | " + sm.getDetail() + " | "
					+ sm.getError());
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}

	}
}