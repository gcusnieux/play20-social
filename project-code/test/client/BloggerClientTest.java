package client;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.junit.Test;

import play.Logger;
import play.Play;

import com.google.gdata.data.Entry;
import com.google.gdata.util.ServiceException;

public class BloggerClientTest {

	@Test
	public void iCanLoadExternalFile() throws IOException {
		URL url = new URL("http://www.google.com");
		URLConnection uc = url.openConnection();
		uc.connect();
		assertTrue(uc.getInputStream().available() > 100);
	}

	@Test
	public void iCanGetProperty() throws ServiceException, IOException {
		String property = Play.application().configuration().getString("blogger.username");
		assertNotNull(property);
	}

	@Test
	public void iCanGetPosts() throws ServiceException, IOException {
		List<Entry> allPosts = BloggerClient.getInstance().getAllPosts();
		assertTrue(allPosts.size() > 1);
	}

	@Test
	public void iCanGetPostById() throws ServiceException, IOException {
		List<Entry> allPosts = BloggerClient.getInstance().getAllPosts();
		Logger.info("selflink : "+allPosts.get(0).getSelfLink().getHref());
		Entry post = BloggerClient.getInstance().getPostById("2919152004917781532");
		assertNotNull(post);
	}

	@Test
	public void iCanGetPostByCategory() throws ServiceException, IOException {
		List<Entry> post = BloggerClient.getInstance().getPostByCategory("2011","2012");
		assertNotNull(post);
		assertEquals(post.size(),1);
	}

	@Test
	public void iCanGetComments() throws ServiceException, IOException {
		List<Entry> allPosts = BloggerClient.getInstance().getAllPosts();
		assertTrue(allPosts.size() > 1);
		List<Entry> allComments = BloggerClient.getInstance().getAllComments(allPosts.get(0));
		assertEquals(allComments.size(), 0);
	}
}
