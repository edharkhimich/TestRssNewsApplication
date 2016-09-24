package com.edgar.rss_app2.parsel;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.edgar.rss_app2.R;
import com.edgar.rss_app2.adapter.MyAdapter;
import com.edgar.rss_app2.extra.LanProgressDialog;
import com.edgar.rss_app2.model.News;

import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class RssParsel extends AsyncTask<Void, Void, Void> {

    private static final String URL = "http://www.cbc.ca/cmlink/rss-topstories";
    Context context;
    LanProgressDialog dialog;
    ArrayList<News> feedItems;
    RecyclerView recyclerView;
    URL url;

    public RssParsel(Context context, RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.context = context;
        dialog = new LanProgressDialog(context);
        dialog.setTitle(R.string.loading);
    }

    @Override
    protected void onPreExecute() {
        dialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        dialog.dismiss();

        MyAdapter adapter = new MyAdapter(context, feedItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected Void doInBackground(Void... params) {
        ProcessXml(Getdata());

        return null;
    }

    private void ProcessXml(Document data) {
        if (data != null) {
            feedItems = new ArrayList<>();
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items = channel.getChildNodes();
            for (int i = 0; i < items.getLength(); i++) {
                Node cureentchild = items.item(i);
                if (cureentchild.getNodeName().equalsIgnoreCase(context.getString(R.string.itemStr))) {
                    News item = new News();
                    NodeList itemchilds = cureentchild.getChildNodes();
                    for (int j = 0; j < itemchilds.getLength(); j++) {
                        Node cureent = itemchilds.item(j);
                        if (cureent.getNodeName().equalsIgnoreCase(context.getString(R.string.titleStr))) {
                            item.setTitle(cureent.getTextContent());
                        } else if (cureent.getNodeName().equalsIgnoreCase(context.getString(R.string.descriptionStr))) {
                            item.setDescription(cureent.getTextContent());
                            String input = item.getDescription();
                            org.jsoup.nodes.Document doc = Jsoup.parse(input);
                            String output = doc.select("img").first().attr("src");
                            item.setImage(output);
                        } else if (cureent.getNodeName().equalsIgnoreCase(context.getString(R.string.putDateStr))) {
                            item.setPubDate(cureent.getTextContent());
                        } else if (cureent.getNodeName().equalsIgnoreCase(context.getString(R.string.linkStr))) {
                            item.setLink(cureent.getTextContent());
                        } else if (cureent.getNodeName().equalsIgnoreCase(context.getString(R.string.authorStr))) {
                            item.setAuthor(cureent.getTextContent());
                        }
                    }
                    feedItems.add(item);
                }
            }
        }
    }

    public Document Getdata() {
        try {
            url = new URL(URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDoc = builder.parse(inputStream);
            return xmlDoc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}