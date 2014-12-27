package net.brotzeller.topeer.topo;


import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import net.brotzeller.topeer.exception.TopoException;



import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by martin on 22.12.14.
 */
public class TopoContent{

    protected String archivename; // name of the file archive
    public Map<String, byte[]> content; // raw content of the archive
    public String imagename;
    protected Activity a;
    public ArrayList<RouteInfo> routes;
    public Map<String, String> texts;

    public TopoContent (String archivename, Activity a) {
        this.archivename = archivename;
        this.a = a;
    }


    public void initialize () throws TopoException {
        content = new HashMap<String, byte[]>();
        openArchive();
        analyzeTextfile(content.get("routes.xml"));

    }

    public byte[] getContent(String idx)
    {
        return content.get(idx);
    }

    public byte[] getImage()
    {
        return getContent(imagename);
    }

    protected void openArchive() throws TopoException
    {
        try {
            InputStream is;
            if (archivename.contains("/")) {
                is = new FileInputStream(archivename);
            } else {
                is = a.getResources().getAssets().open(archivename);
            }
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is));
            try {
                ZipEntry ze;
                while ((ze = zis.getNextEntry()) != null) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int count;
                    while ((count = zis.read(buffer)) != -1) {
                        baos.write(buffer, 0, count);
                    }
                    String filename = ze.getName();
                    byte[] bytes = baos.toByteArray();
                    content.put(filename, bytes);
                }
            } catch (Exception e) {
                Toast.makeText(a, e.getClass().getName()+" l 62: "+e.getMessage(), Toast.LENGTH_LONG).show();
            } finally {
                zis.close();
            }
        } catch(Exception e) {
            Toast.makeText(a, e.getClass().getName()+" l 67: "+ e.getMessage(), Toast.LENGTH_LONG).show();
            throw new TopoException("Error opening "+archivename, e);
        }
    }

    protected void analyzeTextfile(byte[] filename) throws TopoException
    {
        xmlAnalyzer xmlAnalyzer;
        try {
            xmlAnalyzer = net.brotzeller.topeer.topo.xmlAnalyzer.getInstance();
            xmlAnalyzer.setInput(filename);
            xmlAnalyzer.analyzeFile();
            routes = xmlAnalyzer.getRoutes();
            texts = xmlAnalyzer.getTexts();
            imagename = xmlAnalyzer.imagename;
        } catch(Exception e) {
            throw new TopoException("Error reading topo "+archivename+" "+e.getClass(), e);
        }
    }
}
