package net.brotzeller.topeer.topo;


import android.app.Activity;
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
    public Map<String, Pair<String, Integer>> features;

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
            features = collectFeatures(topo.features);
            
        }
        catch (TopoException e) {
            Toast.makeText(a, e.getClass() + ": Error Occured " + e.getMessage(), Toast.LENGTH_LONG).show();
            throw e;
        }
    }
    protected HashMap<String, Pair<String, Integer>> collectFeatures(TopoType.Features features) {
        HashMap<String, Pair<String, Integer>> feats = new HashMap<String, Pair<String, Integer>>();
        Integer icon;
        Pair<String, Integer> content;
        if (features.height !=  null) {
            icon = R.drawable.icon_height;
            content = new Pair<String, Integer>(features.height, icon);
            feats.put("Wandhöhe", content);
        }
        if (features.hikeminutes != null) {
            icon = R.drawable.icon_hike;
            content = new Pair<String, Integer>(features.hikeminutes + " Minute(n)", icon);
            feats.put("Zustieg", content);
        }
        if (features.children != null) {
            if (features.children.equals("yes")) {
                icon = R.drawable.icon_toddler;
                content = new Pair<String, Integer>("ja", icon);
            } else if (features.children.equals("no")) {
                icon = R.drawable.icon_notoddler;
                content = new Pair<String, Integer>("nein", icon);
            } else {
                icon = R.drawable.icon_notoddler;
                content = new Pair<String, Integer>("unter Aufsicht", icon);
            }
            feats.put("Kinderfreundlich", content);
        }
        for(String ali : features.alignment) {
            String title = "Himmelsrichtung";
            TopoType.AlignmentEnum iAli = TopoType.AlignmentEnum.valueOf(ali.toUpperCase());
            switch(iAli) {
                case E:
                    icon = R.drawable.icon_compass_e;
                    content = new Pair<String, Integer>("Ost", icon);
                    feats.put(title, content);
                    break;
                case N:
                    icon = R.drawable.icon_compass_n;
                    content = new Pair<String, Integer>("Nord", icon);
                    feats.put(title, content);
                    break;
                case S:
                    icon = R.drawable.icon_compass_s;
                    content = new Pair<String, Integer>("Süd", icon);
                    feats.put(title, content);
                    break;
                case W:
                    icon = R.drawable.icon_compass_w;
                    content = new Pair<String, Integer>("West", icon);
                    feats.put(title, content);
                    break;
                case NW:
                    icon = R.drawable.icon_compass_nw;
                    content = new Pair<String, Integer>("Nordwest", icon);
                    feats.put(title, content);
                    break;
                case SW:
                    icon = R.drawable.icon_compass_sw;
                    content = new Pair<String, Integer>("Südwest", icon);
                    feats.put(title, content);
                    break;
                case NE:
                    icon = R.drawable.icon_compass_ne;
                    content = new Pair<String, Integer>("Nordost", icon);
                    feats.put(title, content);
                    break;
                case SE:
                    icon = R.drawable.icon_compass_se;
                    content = new Pair<String, Integer>("Südost", icon);
                    feats.put(title, content);
                    break;
                default:
                    break;
            }
        }
        for(String inc : features.inclination) {
            String title = "Neigung";
            TopoType.InclinationEnum iInc = TopoType.InclinationEnum.valueOf(inc.toLowerCase());
            switch(iInc) {
                case vertical:
                    icon = R.drawable.icon_vertical;
                    content = new Pair<String, Integer>("senkrecht", icon);
                    feats.put(title, content);
                    break;
                case slanting:
                    icon = R.drawable.icon_slanted;
                    content = new Pair<String, Integer>("abfallend", icon);
                    feats.put(title, content);
                    break;
                case overhanging:
                    icon = R.drawable.icon_overhanging;
                    content = new Pair<String, Integer>("überhängend", icon);
                    feats.put(title, content);
                    break;
                case roof:
                    icon = R.drawable.icon_roof;
                    content = new Pair<String, Integer>("Dach", icon);
                    feats.put(title, content);
                    break;
                default:
                    break;
            }
        }
        for(String sun : features.sunny) {
            String title = "Sonnig";
            TopoType.SunnyEnum iSun = TopoType.SunnyEnum.valueOf(sun.toLowerCase());
            switch(iSun) {
                case morning:
                    icon = R.drawable.icon_morning;
                    content = new Pair<String, Integer>("vormittags", icon);
                    feats.put(title, content);
                    break;
                case afternoon:
                    icon = R.drawable.icon_afternoon;
                    content = new Pair<String, Integer>("nachmittags", icon);
                    feats.put(title, content);
                    break;
                case allday:
                    icon = R.drawable.icon_sun;
                    content = new Pair<String, Integer>("ganztägig", icon);
                    feats.put(title, content);
                    break;
                case shady:
                    icon = R.drawable.icon_shade;
                    content = new Pair<String, Integer>("nie", icon);
                    feats.put(title, content);
                    break;
                default:
                    break;
            }
        }

        return feats;
    }
}
