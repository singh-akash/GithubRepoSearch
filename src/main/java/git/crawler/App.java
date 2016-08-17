package git.crawler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHRepositorySearchBuilder;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedSearchIterable;

public class App {

	public static final String PROPERTIES_FILE = "C:/Akash/properties.txt";
	public static final String SEARCH_TERM = "NOT ersfasqw43sjdfjkljrarahfsdifohgs34_RANDOM_TEXT_fr";
	
	public class Properties {
		public static final String USERNAME = "username";
		public static final String OAUTH_TOKEN = "oauth.token";
		public static final String LANGUAGE = "language";
	}
	
	public String gitUser = "";
	public String oauthToken = "";
	public String language = "";
	
	public static void main(String[] args) {
		App myApp = new App();
		myApp.init();
		myApp.SearchRepositories("2011-01-31");
	}
	
	public void init() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(PROPERTIES_FILE));
			String line;
			Hashtable<String, String> properties = new Hashtable<String, String>();
			while ((line = br.readLine()) != null)
			{
				String[] propArray = line.split("=");
				properties.put(propArray[0], propArray[1]);
			}
			this.gitUser = properties.get(Properties.USERNAME);
			this.oauthToken = properties.get(Properties.OAUTH_TOKEN);
			this.language = properties.get(Properties.LANGUAGE);
			if (this.language == null)
				this.language = "JavaScript";
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void SearchRepositories(String date) {
		try {
			GitHub github = GitHub.connect(this.gitUser, this.oauthToken);
			GHRepositorySearchBuilder searchBuilder = github.searchRepositories();
			searchBuilder.language(this.language);
			searchBuilder.pushed(date);
			searchBuilder.q(SEARCH_TERM);
			PagedSearchIterable<GHRepository> searchIterable = searchBuilder.list();
			Iterator<GHRepository> iterator = searchIterable.iterator();
			while (iterator.hasNext())
			{
				GHRepository repo = iterator.next();
				System.out.println(repo.getUrl());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}