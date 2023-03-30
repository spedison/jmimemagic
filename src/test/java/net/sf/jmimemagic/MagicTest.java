package net.sf.jmimemagic;

import junit.framework.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationAware;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.File;

public class MagicTest extends TestCase {
	private static String gifFile = "test_docs/test.gif";
	private static String pngFile = "test_docs/test.png";
	private static String tifFile = "test_docs/test_nocompress.tif";
	private static String jpgFile = "test_docs/test.jpg";
	private static String textFile = "test_docs/test.txt";
	private static String officeOpenXmlDocumentFile = "test_docs/test_word.docx";
	private static String officeOpenXmlDocumentFile2 = "test_docs/test_word.docm";
	private static String word2KFile = "test_docs/test_word_2000.doc";
	private static String word95File = "test_docs/test_word_6.0_95.doc";
	private static String rtfFile = "test_docs/test.rtf";
	private static String officeOpenXmlWorkbookFile = "test_docs/test_excel.xlsx";
	private static String officeOpenXmlWorkbookFile2 = "test_docs/test_excel.xlsm";
	private static String officeOpenXmlPresentationFile = "test_docs/test_powerpoint.pptx";
	private static String officeOpenXmlPresentationFile2 = "test_docs/test_powerpoint.pptm";
	private static String excel2KFile = "test_docs/test_excel_2000.xls";
	private static String pdfFile = "test_docs/test.pdf";
	private static String psFile = "test_docs/test.ps";
	private static String javaClass12File = "test_docs/test_1.2.class";
	private static String javaClass13File = "test_docs/test_1.3.class";
	private static String javaClass14File = "test_docs/test_1.4.class";
	private static String mp3_128_44_jstereoFile = "test_docs/test_128_44_jstereo.mp3";
	private static String wavFile = "test_docs/test.wav";
	private static String odtFile = "test_docs/test.odt";
	private static String zipFile = "test_docs/test.zip";
	private static String txtutf8File = "test_txts/utf-8.txt";
	private static String txtAsciiFile = "test_txts/ascii.txt";

	private static Logger log = LogManager.getLogger(MagicTest.class);

	public static void main(String args[]) {
		junit.textui.TestRunner.run(MagicTest.class);
	}

	protected void setUp() throws Exception {
		ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();

		builder.setStatusLevel(Level.DEBUG);
// naming the logger configuration
		builder.setConfigurationName("DefaultLogger");

// create a console appender
		AppenderComponentBuilder appenderBuilder = builder.newAppender("Console", "CONSOLE")
				.addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT);

// add a layout like pattern, json etc
		appenderBuilder.add(builder.newLayout("PatternLayout")
				.addAttribute("pattern", "%d %p %c [%t] %m%n"));
		RootLoggerComponentBuilder rootLogger = builder.newRootLogger(Level.DEBUG);
		rootLogger.add(builder.newAppenderRef("Console"));

		builder.add(appenderBuilder);
		builder.add(rootLogger);
		Configurator.reconfigure(builder.build());
	}

	// IMAGE TESTS
	public void testGIF() {
		log.debug("testing GIF image...");
		try {
			MagicMatch match = Magic.getMagicMatch(new File(gifFile), true, false);
			if (match != null) {
				assertEquals("image/gif", match.getMimeType());
			} else {
				log.debug("failed");
				fail("no match in testGIF()");
			}
			log.debug("ok");
		} catch (Exception e) {
			e.printStackTrace();
			fail("exception in testGIF(). message: " + e);
		} catch (Error e) {
			e.printStackTrace();
			fail("error in testGIF(). message: " + e.getMessage());
		}
	}

	public void testPNG() {
		log.debug("testing PNG image...");
		try {
			MagicMatch match = Magic.getMagicMatch(new File(pngFile), true, false);
			if (match != null) {
				assertEquals("image/png", match.getMimeType());
			} else {
				log.debug("failed");
				fail("no match in testPNG()");
			}
			log.debug("ok");
		} catch (Exception e) {
			e.printStackTrace();
			fail("exception in testPNG(). message: " + e);
		} catch (Error e) {
			e.printStackTrace();
			fail("error in testPNG(). message: " + e.getMessage());
		}
	}

	public void testTextUTF8() {
		log.debug("testing txt utf-8...");
		try {
			MagicMatch match = Magic.getMagicMatch(new File(txtutf8File), true, false);
			if (match != null) {
				log.debug(match);
				assertEquals("text/plain", match.getMimeType());
			} else {
				log.debug("failed");
				fail("no match in testPNG()");
			}
			log.debug("ok");
		} catch (Exception e) {
			e.printStackTrace();
			fail("exception in testTextUTF8(). message: " + e);
		} catch (Error e) {
			e.printStackTrace();
			fail("error in testTextUTF8(). message: " + e.getMessage());
		}
	}

	public void testTextAscii() {
		log.debug("testing txt ascii...");
		try {
			MagicMatch match = Magic.getMagicMatch(new File(txtAsciiFile), false, false);
			if (match != null) {
				log.debug(match);
				assertEquals("text/plain - ISO-8859-1", match.getMimeType());
			} else {
				log.debug("failed");
				fail("no match in testPNG()");
			}
			log.debug("ok");
		} catch (Exception e) {
			e.printStackTrace();
			fail("exception in testTextUTF8(). message: " + e);
		} catch (Error e) {
			e.printStackTrace();
			fail("error in testTextUTF8(). message: " + e.getMessage());
		}
	}

	public void testTIF() {
		log.debug("testing TIF image...");
		try {
			MagicMatch match = Magic.getMagicMatch(new File(tifFile), true, false);
			if (match != null) {
				assertEquals("image/tiff", match.getMimeType());
			} else {
				log.debug("failed");
				fail("no match in testTIF()");
			}
			log.debug("ok");
		} catch (Exception e) {
			e.printStackTrace();
			fail("exception in testTIF(). message: " + e);
		} catch (Error e) {
			e.printStackTrace();
			fail("error in testTIF(). message: " + e.getMessage());
		}
	}

	public void testJPEG() {
		log.debug("testing JPEG image...");
		try {
			MagicMatch match = Magic.getMagicMatch(new File(jpgFile), true, false);
			if (match != null) {
				assertEquals("image/jpeg", match.getMimeType());
			} else {
				log.debug("failed");
				fail("no match in testJPEG()");
			}
			log.debug("ok");
		} catch (Exception e) {
			e.printStackTrace();
			fail("exception in testJPEG(). message: " + e);
		} catch (Error e) {
			e.printStackTrace();
			fail("error in testJPEG(). message: " + e.getMessage());
		}
	}

	// DOCUMENT TESTS

	public void testText() {
		log.debug("testing Plain Text Document...");
		try {
			MagicMatch match = Magic.getMagicMatch(new File(textFile), true, false);
			if (match != null) {
				assertEquals("text/plain", match.getMimeType());
			} else {
				log.debug("failed");
				fail("no match in testText()");
			}
			log.debug("ok");
		} catch (Exception e) {
			e.printStackTrace();
			fail("exception in testText(). message: " + e);
		} catch (Error e) {
			e.printStackTrace();
			fail("error in testText(). message: " + e.getMessage());
		}
	}

	public void testOfficeOpenXmlDocument() {
		log.debug("testing Word Office Open XML Document...");
		try {
			MagicMatch match = Magic.getMagicMatch(new File(officeOpenXmlDocumentFile), true, false);
			if (match != null) {
				assertEquals("application/vnd.openxmlformats-officedocument.wordprocessingml.document", match.getMimeType());
			} else {
				log.debug("failed");
				fail("no match in testOfficeOpenXmlDocument()");
			}
			log.debug("ok");
		} catch (Exception e) {
			e.printStackTrace();
			fail("exception in testOfficeOpenXmlDocument(). message: " + e);
		} catch (Error e) {
			e.printStackTrace();
			fail("error in testOfficeOpenXmlDocument(). message: " + e.getMessage());
		}
	}

	public void testOfficeOpenXmlDocument2() {
		log.debug("testing Word Office Open XML Document...");
		try {
			MagicMatch match = Magic.getMagicMatch(new File(officeOpenXmlDocumentFile2), true, false);
			if (match != null) {
				assertEquals("application/vnd.openxmlformats-officedocument.wordprocessingml.document", match.getMimeType());
			} else {
				log.debug("failed");
				fail("no match in testOfficeOpenXmlDocument2()");
			}
			log.debug("ok");
		} catch (Exception e) {
			e.printStackTrace();
			fail("exception in testOfficeOpenXmlDocument2(). message: " + e);
		} catch (Error e) {
			e.printStackTrace();
			fail("error in testOfficeOpenXmlDocument2(). message: " + e.getMessage());
		}
	}

	public void testWord2K() {
		log.debug("testing Word 2000 Document...");
		try {
			MagicMatch match = Magic.getMagicMatch(new File(word2KFile), true, false);
			if (match != null) {
				assertEquals("application/msword", match.getMimeType());
			} else {
				log.debug("failed");
				fail("no match in testWord2K()");
			}
			log.debug("ok");
		} catch (Exception e) {
			e.printStackTrace();
			fail("exception in testWord2K(). message: " + e);
		} catch (Error e) {
			e.printStackTrace();
			fail("error in testWord2K(). message: " + e.getMessage());
		}
	}

	public void testWord95() {
		log.debug("testing Word 95 Document...");
		try {
			MagicMatch match = Magic.getMagicMatch(new File(word95File), true, false);
			if (match != null) {
				assertEquals("application/msword", match.getMimeType());
			} else {
				log.debug("failed");
				fail("no match in testWord95()");
			}
			log.debug("ok");
		} catch (Exception e) {
			e.printStackTrace();
			fail("exception in testWord95(). message: " + e);
		} catch (Error e) {
			e.printStackTrace();
			fail("error in testWord95(). message: " + e.getMessage());
		}
	}

	public void testRTF() {
		log.debug("testing RTF Document...");
		try {
			MagicMatch match = Magic.getMagicMatch(new File(rtfFile), true, false);
			if (match != null) {
				assertEquals("text/rtf", match.getMimeType());
			} else {
				log.debug("failed");
				fail("no match in testRTF()");
			}
			log.debug("ok");
		} catch (Exception e) {
			e.printStackTrace();
			fail("exception in testRTF(). message: " + e);
		} catch (Error e) {
			e.printStackTrace();
			fail("error in testRTF(). message: " + e.getMessage());
		}
	}

	public void testOfficeOpenXmlWorkbook() {
		log.debug("testing Word Office Open XML Workbook...");
		try {
			MagicMatch match = Magic.getMagicMatch(new File(officeOpenXmlWorkbookFile), true, false);
			if (match != null) {
				assertEquals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", match.getMimeType());
			} else {
				log.debug("failed");
				fail("no match in testOfficeOpenXmlWorkbook()");
			}
			log.debug("ok");
		} catch (Exception e) {
			e.printStackTrace();
			fail("exception in testOfficeOpenXmlWorkbook(). message: " + e);
		} catch (Error e) {
			e.printStackTrace();
			fail("error in testOfficeOpenXmlWorkbook(). message: " + e.getMessage());
		}
	}

	public void testOfficeOpenXmlWorkbook2() {
		log.debug("testing Word Office Open XML Workbook...");
		try {
			MagicMatch match = Magic.getMagicMatch(new File(officeOpenXmlWorkbookFile2), true, false);
			if (match != null) {
				assertEquals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", match.getMimeType());
			} else {
				log.debug("failed");
				fail("no match in testOfficeOpenXmlWorkbook2()");
			}
			log.debug("ok");
		} catch (Exception e) {
			e.printStackTrace();
			fail("exception in testOfficeOpenXmlWorkbook2(). message: " + e);
		} catch (Error e) {
			e.printStackTrace();
			fail("error in testOfficeOpenXmlWorkbook2(). message: " + e.getMessage());
		}
	}
    
	public void testExcel2K() {
		log.debug("testing Excel 2000 Document...");
		try {
			MagicMatch match = Magic.getMagicMatch(new File(excel2KFile), true, false);
			if (match != null) {
				assertEquals("application/msword", match.getMimeType());
			} else {
				log.debug("failed");
				fail("no match in testExcel2K()");
			}
			log.debug("ok");
		} catch (Exception e) {
			e.printStackTrace();
			fail("exception in testExcel2K(). message: " + e);
		} catch (Error e) {
			e.printStackTrace();
			fail("error in testExcel2K(). message: " + e.getMessage());
		}
	}


	public void testOfficeOpenXmlPresentation() {
		log.debug("testing Word Office Open XML Presentation...");
		try {
			MagicMatch match = Magic.getMagicMatch(new File(officeOpenXmlPresentationFile), true, false);
			if (match != null) {
				assertEquals("application/vnd.openxmlformats-officedocument.presentationml.presentation", match.getMimeType());
			} else {
				log.debug("failed");
				fail("no match in testOfficeOpenXmlPresentation()");
			}
			log.debug("ok");
		} catch (Exception e) {
			e.printStackTrace();
			fail("exception in testOfficeOpenXmlPresentation(). message: " + e);
		} catch (Error e) {
			e.printStackTrace();
			fail("error in testOfficeOpenXmlPresentation(). message: " + e.getMessage());
		}
	}

	public void testOfficeOpenXmlPresentation2() {
		log.debug("testing Word Office Open XML Presentation...");
		try {
			MagicMatch match = Magic.getMagicMatch(new File(officeOpenXmlPresentationFile2), true, false);
			if (match != null) {
				assertEquals("application/vnd.openxmlformats-officedocument.presentationml.presentation", match.getMimeType());
			} else {
				log.debug("failed");
				fail("no match in testOfficeOpenXmlPresentation2()");
			}
			log.debug("ok");
		} catch (Exception e) {
			e.printStackTrace();
			fail("exception in testOfficeOpenXmlPresentation2(). message: " + e);
		} catch (Error e) {
			e.printStackTrace();
			fail("error in testOfficeOpenXmlPresentation2(). message: " + e.getMessage());
		}
	}

	// OTHER

	public void testPDF() {
		log.debug("testing PDF Document...");
		try {
			MagicMatch match = Magic.getMagicMatch(new File(pdfFile), true, false);
			if (match != null) {
				assertEquals("application/pdf", match.getMimeType());
			} else {
				log.debug("failed");
				fail("no match in testPDF()");
			}
			log.debug("ok");
		} catch (Exception e) {
			e.printStackTrace();
			fail("exception in testPDF(). message: " + e);
		} catch (Error e) {
			e.printStackTrace();
			fail("error in testPDF(). message: " + e.getMessage());
		}
	}

	public void testPostscript() {
		log.debug("testing Postscript Document...");
		try {
			MagicMatch match = Magic.getMagicMatch(new File(psFile), true, false);
			if (match != null) {
				assertEquals("application/postscript", match.getMimeType());
			} else {
				log.debug("failed");
				fail("no match in testPostscript()");
			}
			log.debug("ok");
		} catch (Exception e) {
			e.printStackTrace();
			fail("exception in testPostscript(). message: " + e);
		} catch (Error e) {
			e.printStackTrace();
			fail("error in testPostscript(). message: " + e.getMessage());
		}
	}

	public void testJavaClass12() {
		log.debug("testing Java Class File (v1.2)...");
		try {
			MagicMatch match = Magic.getMagicMatch(new File(javaClass12File), true, false);
			if (match != null) {
				assertEquals("application/java", match.getMimeType());
			} else {
				log.debug("failed");
				fail("no match in testJavaClass12()");
			}
			log.debug("ok");
		} catch (Exception e) {
			e.printStackTrace();
			fail("exception in testJavaClass12(). message: " + e);
		} catch (Error e) {
			e.printStackTrace();
			fail("error in testJavaClass12(). message: " + e.getMessage());
		}
	}

	public void testJavaClass13() {
		log.debug("testing Java Class File (v1.3)...");
		try {
			MagicMatch match = Magic.getMagicMatch(new File(javaClass13File), true, false);
			if (match != null) {
				assertEquals("application/java", match.getMimeType());
			} else {
				log.debug("failed");
				fail("no match in testJavaClass13()");
			}
			log.debug("ok");
		} catch (Exception e) {
			e.printStackTrace();
			fail("exception in testJavaClass13(). message: " + e);
		} catch (Error e) {
			e.printStackTrace();
			fail("error in testJavaClass13(). message: " + e.getMessage());
		}
	}

	public void testJavaClass14() {
		log.debug("testing Java Class File (v1.4)...");
		try {
			MagicMatch match = Magic.getMagicMatch(new File(javaClass14File), true, false);
			if (match != null) {
				assertEquals("application/java", match.getMimeType());
			} else {
				log.debug("failed");
				fail("no match in testJavaClass14()");
			}
			log.debug("ok");
		} catch (Exception e) {
			e.printStackTrace();
			fail("exception in testJavaClass14(). message: " + e);
		} catch (Error e) {
			e.printStackTrace();
			fail("error in testJavaClass14(). message: " + e.getMessage());
		}
	}

	public void testMP3() {
		log.debug("testing MPEG Layer 3...");
		try {
			MagicMatch match = Magic.getMagicMatch(new File(mp3_128_44_jstereoFile), true, false);
			if (match != null) {
				assertEquals("audio/mp3", match.getMimeType());
			} else {
				log.debug("failed");
				fail("no match in testMP3()");
			}
			log.debug("ok");
		} catch (Exception e) {
			e.printStackTrace();
			fail("exception in testMP3(). message: " + e);
		} catch (Error e) {
			e.printStackTrace();
			fail("error in testMP3(). message: " + e.getMessage());
		}
	}

	public void testWave() {
		log.debug("testing WAVE...");
		try {
			MagicMatch match = Magic.getMagicMatch(new File(wavFile), true, false);
			if (match != null) {
				assertEquals("RIFF (little-endian) data", match.getDescription());
			} else {
				log.debug("failed");
				fail("no match in testWave()");
			}
			assertTrue(match.descriptionMatches("Microsoft PCM"));
			assertTrue(match.descriptionMatches("stereo"));
			assertTrue(match.mimeTypeMatches("application/x-wav"));
			log.debug("ok");
		} catch (Exception e) {
			e.printStackTrace();
			fail("exception in testWave(). message: " + e);
		} catch (Error e) {
			e.printStackTrace();
			fail("error in testWave(). message: " + e.getMessage());
		}
	}
	public void testOdt(){
		log.debug("testing ODT Document...");
		try {
			MagicMatch match = Magic.getMagicMatch(new File(odtFile), true, false);
			if (match != null) {
				assertEquals("application/vnd.oasis.opendocument.text", match.getMimeType());
			} else {
				log.debug("failed");
				fail("no match in testOdt()");
			}
			log.debug("ok");
		} catch (Exception e) {
			e.printStackTrace();
			fail("exception in testOdt(). message: " + e);
		} catch (Error e) {
			e.printStackTrace();
			fail("error in testOdt(). message: " + e.getMessage());
		}
		
	}
	
	public void testZip(){
		log.debug("testing Zip File...");
		try {
			MagicMatch match = Magic.getMagicMatch(new File(zipFile), true, false);
			if (match != null) {
				assertEquals("application/zip", match.getMimeType());
			} else {
				log.debug("failed");
				fail("no match in testOdt()");
			}
			log.debug("ok");
		} catch (Exception e) {
			e.printStackTrace();
			fail("exception in testZip(). message: " + e);
		} catch (Error e) {
			e.printStackTrace();
			fail("error in testZip(). message: " + e.getMessage());
		}
		
	}
	   
    public void testSubMatches(){
        log.debug("testing Submatches...");
        try {
            MagicMatch match = Magic.getMagicMatch(new File(gifFile), true, false);
            Assert.assertEquals(3, match.getSubMatches().size());
            match = Magic.getMagicMatch(new File(gifFile), true, false);
            Assert.assertEquals(3, match.getSubMatches().size());           
            log.debug("ok");
        } catch (Exception e) {
            e.printStackTrace();
            fail("exception in testSubMatches(). message: " + e);
        } catch (Error e) {
            e.printStackTrace();
            fail("error in testSubMatches(). message: " + e.getMessage());
        }
        
    }

}
