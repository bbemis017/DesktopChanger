import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;
import javax.swing.text.AttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;



public class Change {
	
	static final String changeDesktopLocation = "D:\\CodeLibrary\\JavaLibrary\\ImageExtract\\changeDesktop";
	
	static final String astronomyUrl = "http://apod.nasa.gov/apod/astropix.html";
	static final String sourceUrl = "http://apod.nasa.gov/";
	static final String path = "./day.bmp";
	
	public static void main(String args[]){
		
		try{
			URL url = new URL(astronomyUrl);
			URLConnection connection = url.openConnection();
			java.io.InputStream is = connection.getInputStream();

			InputStreamReader isr = new InputStreamReader(is);

			BufferedReader br = new BufferedReader(isr);
			
			HTMLEditorKit htmlKit = new HTMLEditorKit();
			HTMLDocument htmlDoc = (HTMLDocument) htmlKit.createDefaultDocument();
			HTMLEditorKit.Parser parser = new ParserDelegator();
			HTMLEditorKit.ParserCallback callback = htmlDoc.getReader(0);
			parser.parse(br, callback, true);
			
			int i = 0;
			for(HTMLDocument.Iterator iterator = htmlDoc.getIterator(HTML.Tag.A); iterator.isValid();iterator.next()){
				++i;
				AttributeSet attributes = iterator.getAttributes();
				if(i==2){
					System.out.println( attributes.toString() );
					String attribute = attributes.toString();
					String location = attribute.substring(5,attribute.length()-1);
					System.out.println(location);
					downloadImage(sourceUrl + location);
					resetWallpaper();
				}
			}
			
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	private static void resetWallpaper(){
		String os = System.getProperty("os.name");
		if(os.startsWith("Windows")){
			System.out.println("start batch");
			try{
			Runtime.getRuntime().exec("cmd /c start " + changeDesktopLocation);
			}catch(Exception e){
				System.err.println(e);
			}
		}else if(os.startsWith("Mac")){
			
		}else{
			System.out.println("linux");
		}
	}

	private static void downloadImage(String string) {
		BufferedImage image = null;
		String format = "bmp";
		
		try{
			URL imageUrl = new URL(string);
			image = ImageIO.read(imageUrl);
			
			if(image!=null){
				System.out.println();
				File file = new File(path);
				ImageIO.write(image, format, file);
				
				System.out.println("success");
			}
			
		}catch(Exception e){
			System.err.println(e);
		}
		
	}

}
