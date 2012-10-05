package client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.junit.Test;

import com.google.gdata.data.Link;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.GphotoEntry;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.util.ServiceException;

public class PicasaClientTest {

	@Test
	public void iCanCreateClient() {
		PicasaClient client = PicasaClient.getInstance();
		assertNotNull(client);
	}

	@Test
	public void iCanLoadExternalFile() throws IOException {
		URL url = new URL("http://www.google.com");
		URLConnection uc = url.openConnection();
		uc.connect();
		assertTrue(uc.getInputStream().available() > 100);
	}

	@Test
	public void iCanAuthentiateClient() {
		PicasaClient client = PicasaClient.getInstance();
		assertNotNull(client);
	}

	@Test
	public void iCanGetAlbums() throws IOException, ServiceException {
		PicasaClient client = PicasaClient.getInstance();
		List<AlbumEntry> albums = client.getAlbums();
		assertNotNull(albums);
		assertEquals(albums.size(), 3);
		AlbumFeed feed = albums.get(0).getFeed();

		for (GphotoEntry photo : feed.getPhotoEntries()) {
			System.out.println(photo.getTitle().getPlainText());


			((PhotoEntry)photo).getMediaThumbnails().get(0).getUrl();
			Link feedLink = photo.getFeedLink();

			System.out.println(feedLink.getHref());
		}

		assertEquals(feed.getPhotoEntries().size(), 2);

	}
}
