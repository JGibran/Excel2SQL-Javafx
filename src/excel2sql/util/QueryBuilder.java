/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excel2sql.util;

/**
 *
 * @author Cristian
 */
public class QueryBuilder {
    
    public static String INSERT = "INSERT INTO ";
	
	public static String getQuery(String table, String[] params, String[] values){
		String query = INSERT + table + "(";
		for(int i = 0; i<params.length; i++){
			if(i < params.length -1){
				query += params[i] + ", " ;
			}else{
				query += params[i];
			}
		}
		query += ") VALUES (";
		
		for(int i = 0; i<values.length; i++){
			if(i < values.length -1){
				query += "'" + values[i] + "', ";
			}else{
				query += "'" + values[i] + "'";
			}
		}
		query += ");";
		return query;
	}
    
}
