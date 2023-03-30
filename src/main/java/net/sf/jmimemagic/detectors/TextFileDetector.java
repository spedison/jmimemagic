/*
jMimeMagic (TM) is a Java Library for determining the content type of files or streams
Copyright (C) 2003-2017 David Castro
*/
package net.sf.jmimemagic.detectors;

import net.sf.jmimemagic.MagicDetector;

import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.BOMInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Pattern;


/**
 * DOCUMENT ME!
 *
 * @author $Author$
 * @version $Revision$
  */
public class TextFileDetector implements MagicDetector
{

    public static final ByteOrderMark UTF_32LE = new ByteOrderMark("UTF-32LE", new int[]{0xFF, 0xFE, 0, 0});
    public static final ByteOrderMark UTF_32BE = new ByteOrderMark("UTF-32BE", new int[]{0, 0, 0xFE, 0xFF});

    private static Logger log = LogManager.getLogger(TextFileDetector.class);

    /**
     * Creates a new TextFileDetector object.
     */
    public TextFileDetector()
    {
        super();
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getDisplayName()
    {
        return "Text File Detector";
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getVersion()
    {
        return "0.1";
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String[] getHandledExtensions()
    {
        return new String[] { "txt", "text" };
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String[] getHandledTypes()
    {
        return new String[] { "text/plain", "text/plain - UTF-8" , "text/plain - UTF-16", "text/plain - UTF-32", "text/plain - ISO-8859-1"};
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getName()
    {
        return "textfiledetector";
    }

    /**
     * DOCUMENT ME!
     *
     * @param data DOCUMENT ME!
     * @param offset DOCUMENT ME!
     * @param length DOCUMENT ME!
     * @param bitmask DOCUMENT ME!
     * @param comparator DOCUMENT ME!
     * @param mimeType DOCUMENT ME!
     * @param params DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String[] process(byte[] data, int offset, int length, long bitmask, char comparator,
        String mimeType, Map<String,String> params)
    {
        log.debug("processing stream data");

        BOMInputStream bomIn = null;
        try {

            boolean isIso8859_1 = true;
            for (int i = 0; i < length; i++) {
                int b = data[i] & 0xFF;
                if (b > 0x7F && b < 0xA0) {
                    isIso8859_1 = false;
                    break;
                }
            }

            if (isIso8859_1) {
                return new String[] { getHandledTypes()[4] };
            }

            bomIn = new BOMInputStream(new ByteArrayInputStream(data), UTF_32LE, UTF_32BE);
            if (bomIn.hasBOM()) {
                return new String[] { getHandledTypes()[3] };
            }

            bomIn = new BOMInputStream(new ByteArrayInputStream(data), ByteOrderMark.UTF_16LE, ByteOrderMark.UTF_16BE);
            if (bomIn.hasBOM()) {
                return new String[] { getHandledTypes()[2] };
            }

            bomIn = new BOMInputStream(new ByteArrayInputStream(data), ByteOrderMark.UTF_8);
            if (bomIn.hasBOM()) {
                return new String[] { getHandledTypes()[1] };
            }

            String s = new String(data, StandardCharsets.US_ASCII);

            if (!Pattern.matches("/[^[:ascii:][:space:]]/", s)) {
                return new String[] { getHandledTypes()[0] };
            }


        } catch (IOException e) {
            log.error("TextFileDetector: error detecting byte order mark");
        } finally {
        	IOUtils.closeQuietly(bomIn);
        }

        return null;
    }

    /**
     * DOCUMENT ME!
     *
     * @param file DOCUMENT ME!
     * @param offset DOCUMENT ME!
     * @param length DOCUMENT ME!
     * @param bitmask DOCUMENT ME!
     * @param comparator DOCUMENT ME!
     * @param mimeType DOCUMENT ME!
     * @param params DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String[] process(File file, int offset, int length, long bitmask, char comparator,
        String mimeType, Map<String,String> params)
    {
        log.debug("processing file data");

        BufferedInputStream is =null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));

            byte[] b = new byte[length];
            int n = is.read(b, offset, length);
            if (n > 0) {
                return process(b, offset, length, bitmask, comparator, mimeType, params);
            }
        } catch (IOException e) {
            log.error("TextFileDetector: error", e);
        } finally {
        	IOUtils.closeQuietly(is);
        }

        return null;
    }
}
