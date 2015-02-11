package net.brotzeller.topeer.topo;


import android.content.Context;
import android.widget.Toast;

import net.brotzeller.topeer.exception.TopoException;
import net.brotzeller.topeer.xml.*;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class TopoContent{

    protected String archivename; // name of the file archive
    public Map<String, byte[]> content; // raw content of the archive
    public String imagename;
    protected Context a;
    public ArrayList<Routetype> routes;
    public Map<String, String> texts;
    public TopoType.Features features;

    public TopoContent (String archivename, Context a) {
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
                routes.size(),
                getRouteHistBins()
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
                if (null == a) {
                    throw new TopoException("No context while opening "+archivename);
                }
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
            Toast.makeText(a, e.getClass() + ": Error Occured " + archivename + ": " + e.getMessage(), Toast.LENGTH_LONG).show();
            throw e;
        }
    }
    public int[] getRouteHistBins() {
        int[] bins = {0,0,0,0};
        for(Routetype route : routes) {
            if(route.difficulty==null) {
                continue;
            }
            Pattern p = Pattern.compile("^(\\d*)");
            Matcher m = p.matcher(route.difficulty);
            m.find();
            int i = Integer.parseInt("0" + m.group(1));
            switch(i) {
                case 1:case 2:case 3:case 4:case 5:
                    bins[0]++;
                    break;
                case 6:case 7:
                    bins[1]++;
                    break;
                case 8:case 9:
                    bins[2]++;
                    break;
                case 10:case 11:
                    bins[3]++;
                    break;
            }
        }
        return bins;
    }
}
