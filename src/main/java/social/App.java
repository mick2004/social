package social;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class App {

	public static void main(String[] args) {

		String site_subdomain = "https://sumobet.api.oneall.com";
		String site_public_key = "56fcd00e-ba06-4a16-903c-6853d1ecad1e";
		String site_private_key = "8ed49f3b-32cc-4dff-a85d-1b45152ca35d";

		// API Access Domain
		String site_domain = "sumobet" + ".api.oneall.com";

		// Connection Resource
		//String resource_uri = "https://" + site_domain + "/connections.json";
		//String resource_uri = "https://" + site_domain + "/users.json";
		String resource_uri = "https://" + site_domain + "/users/21b6a652-de48-46f7-a80c-383093328c6d.json";

		// Result Container
		String result_json = "";

		String result = null;
		try {
			// Forge authentication string username:password
			String site_authentication = site_public_key + ":" + site_private_key;
			String encoded_site_authentication = new String(Base64.getEncoder().encodeToString(site_authentication.getBytes()))
					.replaceAll("[\n\r]", "");

			// Setup connection
			URL url = new URL(resource_uri);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Referer", "https://sumobet.co.uk");

			// Connect using basic auth
			//connection.setRequestMethod("GET");
			connection.setRequestMethod("DELETE");
			connection.setRequestProperty("Authorization", "Basic " + encoded_site_authentication);
			connection.setRequestProperty("confirm_deletion", "true");
			connection.setDoOutput(true);
			connection.setReadTimeout(10000);
			connection.connect();
			connection.getInputStream();

			StringBuilder sb = new StringBuilder();
			String line = null;

			// Read result
			BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			result = sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Done
		System.out.println(result);

	}

}
