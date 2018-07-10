package com.codemonkey.dbMigration.jdbc;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScriptRunner {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public void execute(Connection connection, Reader reader)
			throws IOException, SQLException {
			
		final boolean originalAutoCommit = connection.getAutoCommit();
			
		try {
			if (originalAutoCommit) {
				connection.setAutoCommit(false);
			}
			doExecute(connection, reader);
		} finally {
			connection.setAutoCommit(originalAutoCommit);
		}
	}

	private void doExecute(Connection connection, Reader reader)
			throws IOException, SQLException {
		LineNumberReader lineReader = new LineNumberReader(reader);
		List<String> sqlList = new ArrayList<String>();
		//从文件中提取可执行的sql语句
		sqlList = getSqlList(lineReader);
		for( String sql  : sqlList ){
			executeStatement(connection, sql);
		}
	}

	private List<String> getSqlList( LineNumberReader lineReader ) 
			throws IOException {
		List<String> sqlList = new ArrayList<String>();
		String line;
		boolean funcFlag = false;
		StringBuffer sqlStr = new StringBuffer();
		while ((line = lineReader.readLine()) != null) {
			
			if(line.trim().toUpperCase().indexOf("--START") >= 0){//函数 开始
				funcFlag = true;
				sqlStr = new StringBuffer();
			}else if(line.trim().toUpperCase().indexOf("--END") >= 0){//函数结束
				funcFlag = false;
				if(  sqlStr != null && !("").equals(sqlStr)){
					sqlList.add(sqlStr.toString());
				}
				sqlStr = new StringBuffer();
			}
			
			if(funcFlag){ // 函数内sql
				if (!line.trim().startsWith("--") && !line.trim().startsWith("#") && !line.trim().startsWith("//")){ //不是注释
					sqlStr.append(line);
					sqlStr.append("\n");
				}
			}else{ // 非函数sql ，用分号分割
				if (!line.trim().startsWith("--") && !line.trim().startsWith("#") && !line.trim().startsWith("//")){ //不是注释
					if(line.toString().indexOf(";") > 0){  //以分号结束；
						sqlStr.append (line.toString().substring(0, line.toString().indexOf(";")+1 ) ); //添加本行非注释的部分
						if(sqlStr != null && !("").equals(sqlStr)){
							sqlList.add(sqlStr.toString());
						}
						sqlStr = new StringBuffer();
					}else{//段内sql 
						sqlStr.append(line);
						sqlStr.append("\n");
					}
				}
			}
		}
		return sqlList;

	}
	private void executeStatement(Connection conn, String command)
			throws SQLException {
		Statement statement = conn.createStatement();

		logger.info(command);

		conn.setAutoCommit(false);
		boolean hasResults = statement.execute(command);
		ResultSet rs = statement.getResultSet();

		if (hasResults && rs != null) {
			ResultSetMetaData md = rs.getMetaData();
			int cols = md.getColumnCount();
			for (int i = 1; i <= cols; i++) {
				String name = md.getColumnName(i);
				logger.debug(name + "\t");
			}
			while (rs.next()) {
				for (int i = 1; i <= cols; i++) {
					String value = rs.getString(i);
					logger.debug(value + "\t");
				}
			}
		}

		try {
			statement.close();
		} catch (Exception e) {
			// Ignore to workaround a bug in Jakarta DBCP
		}
		Thread.yield();
	}

}
