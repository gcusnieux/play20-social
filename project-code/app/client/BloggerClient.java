package client;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import play.Logger;
import play.Play;

import com.google.gdata.client.Query;
import com.google.gdata.client.Query.CategoryFilter;
import com.google.gdata.client.blogger.BloggerService;
import com.google.gdata.data.Category;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.Entry;
import com.google.gdata.data.Feed;
import com.google.gdata.data.Person;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class BloggerClient {

	private static final String METAFEED_URL = "http://www.blogger.com/feeds/default/blogs";
	private static final String FEED_URI_BASE = "http://www.blogger.com/feeds";
	private static final String POSTS_FEED_URI_SUFFIX = "/posts/default";
	private static final String COMMENTS_FEED_URI_SUFFIX = "/comments/default";
	private static BloggerClient singleton = null;
	private BloggerService myService;
	private String feedUri;

	private BloggerClient() {
		String blogid = Play.application().configuration().getString("blogger.blogid");
		Logger.info("Blog id is "+blogid);
		// String profileid =
		// Play.application().configuration().getString("blogger.profileid");
		String name = Play.application().configuration().getString("blogger.name");
		this.feedUri = FEED_URI_BASE + "/" + blogid;
		myService = new BloggerService(name);
		String username = Play.application().configuration().getString("blogger.username");
		String password = Play.application().configuration().getString("blogger.password");
		try {
			myService.setUserCredentials(username, password);
		} catch (AuthenticationException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(
					"Illegal username/password combination.");
		}
	}

	public static BloggerClient getInstance() {
		if (singleton == null) {
			singleton = new BloggerClient();
		}
		return singleton;
	}

	public String getBlogId() throws ServiceException, IOException {
		final URL feedUrl = new URL(METAFEED_URL);
		Feed resultFeed = myService.getFeed(feedUrl, Feed.class);
		if (resultFeed.getEntries().size() > 0) {
			Entry entry = resultFeed.getEntries().get(0);
			return entry.getId().split("blog-")[1];
		}
		throw new IOException("User has no blogs!");
	}

	public void printUserBlogs() throws ServiceException, IOException {

		final URL feedUrl = new URL(METAFEED_URL);
		Feed resultFeed = myService.getFeed(feedUrl, Feed.class);

		System.out.println(resultFeed.getTitle().getPlainText());
		for (int i = 0; i < resultFeed.getEntries().size(); i++) {
			Entry entry = resultFeed.getEntries().get(i);
			System.out.println("\t" + entry.getTitle().getPlainText());
		}
		System.out.println();
	}

	public Entry createPost(String title, String content, String authorName,
			String userName, Boolean isDraft) throws ServiceException,
			IOException {
		Entry myEntry = new Entry();
		myEntry.setTitle(new PlainTextConstruct(title));
		myEntry.setContent(new PlainTextConstruct(content));
		Person author = new Person(authorName, null, userName);
		myEntry.getAuthors().add(author);
		myEntry.setDraft(isDraft);

		URL postUrl = new URL(feedUri + POSTS_FEED_URI_SUFFIX);
		return myService.insert(postUrl, myEntry);
	}

	public List<Entry> getAllPosts() throws ServiceException, IOException {
		URL feedUrl = new URL(feedUri + POSTS_FEED_URI_SUFFIX);
		Feed resultFeed = myService.getFeed(feedUrl, Feed.class);
		return resultFeed.getEntries();
	}

	public Entry getPostById(String postid) throws IOException, ServiceException {
		URL feedUrl = new URL(feedUri + POSTS_FEED_URI_SUFFIX + "/" + postid);
		return myService.getEntry(feedUrl, Entry.class);
	}

	public List<Entry> getPostByCategory(String... categories)
			throws ServiceException, IOException {
		URL feedUrl = new URL(feedUri + POSTS_FEED_URI_SUFFIX);
		Query query = new Query(feedUrl);
		for (String cat : categories) {
			CategoryFilter filter = new CategoryFilter();
			filter.addCategory(new Category(cat));
			query.addCategoryFilter(filter);
		}
		Feed resultFeed = myService.getFeed(query, Feed.class);
		return resultFeed.getEntries();
	}

	public void printDateRangeQueryResults(DateTime startTime, DateTime endTime)
			throws ServiceException, IOException {
		URL feedUrl = new URL(feedUri + POSTS_FEED_URI_SUFFIX);
		Query myQuery = new Query(feedUrl);
		myQuery.setUpdatedMin(startTime);
		myQuery.setUpdatedMax(endTime);
		Feed resultFeed = myService.query(myQuery, Feed.class);

		System.out.println(resultFeed.getTitle().getPlainText()
				+ " posts between " + startTime + " and " + endTime);
		for (int i = 0; i < resultFeed.getEntries().size(); i++) {
			Entry entry = resultFeed.getEntries().get(i);
			System.out.println("\t" + entry.getTitle().getPlainText());
			System.out.println("\t" + entry.getUpdated().toStringRfc822());
		}
		System.out.println();
	}

	public Entry updatePostTitle(Entry entryToUpdate, String newTitle)
			throws ServiceException, IOException {
		entryToUpdate.setTitle(new PlainTextConstruct(newTitle));
		URL editUrl = new URL(entryToUpdate.getEditLink().getHref());
		return myService.update(editUrl, entryToUpdate);
	}

	public Entry createComment(String postId, String commentText)
			throws ServiceException, IOException {
		String commentsFeedUri = feedUri + "/" + postId
				+ COMMENTS_FEED_URI_SUFFIX;
		URL feedUrl = new URL(commentsFeedUri);

		Entry myEntry = new Entry();
		myEntry.setContent(new PlainTextConstruct(commentText));
		return myService.insert(feedUrl, myEntry);
	}

	public List<Entry> getAllComments(Entry entry) throws ServiceException,
			IOException {

		String postId = getPostId(entry);

		String commentsFeedUri = feedUri + "/" + postId
				+ COMMENTS_FEED_URI_SUFFIX;
		URL feedUrl = new URL(commentsFeedUri);
		Feed resultFeed = myService.getFeed(feedUrl, Feed.class);
		return resultFeed.getEntries();
	}

	private String getPostId(Entry post) {
		String selfLinkHref = post.getSelfLink().getHref();
		String[] tokens = selfLinkHref.split("/");
		return tokens[tokens.length - 1];
	}

	public void deleteComment(String editLinkHref) throws ServiceException,
			IOException {
		URL deleteUrl = new URL(editLinkHref);
		myService.delete(deleteUrl);
	}

	public void deletePost(String editLinkHref) throws ServiceException,
			IOException {
		URL deleteUrl = new URL(editLinkHref);
		myService.delete(deleteUrl);

	}

}
