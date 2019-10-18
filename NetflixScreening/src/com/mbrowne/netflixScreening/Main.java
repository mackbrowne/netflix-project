package com.mbrowne.netflixScreening;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main
{
  private final static String INPUT = 
			"{"
		+ "  \"personnel\": ["
		+ "    {"
		+ "      \"name\": \"John\","
		+ "      \"roleID\": 1"
		+ "    },"
		+ "    {"
		+ "      \"name\": \"May\","
		+ "      \"roleID\": 1"
		+ "    },"
		+ "    {"
		+ "      \"name\": \"James\","
		+ "      \"roleID\": 2"
		+ "    },"
		+ "    {"
		+ "      \"name\": \"Gary\","
		+ "      \"roleID\": 3"
		+ "    }"
		+ "  ],"
		+ "  \"roles\": ["
		+ "    {"
		+ "      \"id\": 1,"
		+ "      \"name\": \"Contributor\""
		+ "    },"
		+ "    {"
		+ "      \"id\": 2,"
		+ "      \"name\": \"Lead\""
		+ "    },"
		+ "    {"
		+ "      \"id\": 3,"
		+ "      \"name\": \"Manager\""
		+ "    }"
		+ "  ]"
		+ "}";

	private static String consolidatePersonnel(String json) throws JSONException {
		final JSONObject parsedJson = new JSONObject(json);
		
		final JSONArray personnel = parsedJson.getJSONArray("personnel");
		final JSONArray roles = parsedJson.getJSONArray("roles");
		
		JSONObject output = new JSONObject();
		
		for (int i = 0; i < personnel.length(); ++i) {

			final JSONObject person = personnel.getJSONObject(i);
			final String name = person.getString("name");
			final int roleID = person.getInt("roleID");

			// I noticed that personnel roleID - 1 will be the roles array index
			final int roleIndex = roleID - 1;
			final JSONObject role = roles.getJSONObject(roleIndex);
			final String roleName = role.getString("name");
			
			// Find Existing Employees If Any
			String employees;
			try {
				employees = output.getString(roleName);
			}catch(JSONException e) {
				employees = "";
			}
			
			// Add employee to role
			if(employees.isEmpty()) {
				employees = name;
			}else {
				employees = employees + ", " + name;
			}
			output.put(roleName, employees);
		}
	
		return output.toString();
	}
	
  public static void main(String[] args)
  {
	String output = consolidatePersonnel(INPUT);
    System.out.print(output);
  }
}
