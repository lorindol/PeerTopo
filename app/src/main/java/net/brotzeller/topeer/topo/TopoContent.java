package net.brotzeller.topeer.topo;


import android.app.Activity;
import android.content.res.Resources;
import android.support.v4.util.Pair;
import android.widget.Toast;

import net.brotzeller.topeer.R;
import net.brotzeller.topeer.exception.TopoException;
import net.brotzeller.topeer.xml.*;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class TopoContent{

    protected String archivename; // name of the file archive
    public Map<String, byte[]> content; // raw content of the archive
    public String imagename;
    protected Activity a;
    public ArrayList<Routetype> routes;
    public Map<String, String> texts;
    public TopoType.Features features;

    public TopoContent (String archivename, Activity a) {
        this.archivename = archivename;
        this.a = a;
    }

    public TopoContent (String archivename) {
        this.archivename = archivename;
        this.a = null;
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

    public byte[] getImage() { return getContent(imagename); }

    public TopoOverview.TopoInfo getInfo() {
        return new TopoOverview.TopoInfo(
                archivename,
                texts.get("name"),
                texts.get("description"),
                routes.size()
        );
    }

    public String getText(String id) {
        return texts.get(id);
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

    protected void analyzeTextfile(byte[] filecontent) throws TopoException
    {
        try {

            XmlTopoReader reader = new XmlTopoReader();
            TopoType topo = reader.parseTopo(filecontent);
            routes = (ArrayList) topo.routes;
            texts = reader.getTexts();
            imagename = topo.image.name;
            features = topo.features;
            
        }
        catch (TopoException e) {
            Toast.makeText(a, e.getClass() + ": Error Occured " + e.getMessage(), Toast.LENGTH_LONG).show();
            throw e;
        }
    }
}
