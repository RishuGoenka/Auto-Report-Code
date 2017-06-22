package com.zycus.scriptExecutor.databaseAPI;

import junit.framework.TestCase;

import org.junit.Test;

import com.zycus.scriptExecutor.utility.DatabaseConnectionUtil;

public class DatabaseConnectionAPITest extends TestCase {

	@Override
	protected void setUp() throws Exception {
	}

	@Override
	protected void tearDown() throws Exception {
		DatabaseConnectionUtil.closeConnection();
	}

	@Test
	public void testIsTableExistsMsSql() {
	}

}
