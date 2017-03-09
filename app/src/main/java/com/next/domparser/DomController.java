package com.next.domparser;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.next.domparser.IDocument;
import com.next.domparser.R;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by next on 9/3/17.
 */
public class DomController {
    Context mContext;
    IDocument iDocument;
    NodeList mNodeList;


    public DomController(IDocument iDocument, Context applicationContext) {
        this.iDocument = iDocument;
        this.mContext = applicationContext;
    }
    public void getData(){
        String URL_Conn=mContext.getResources().getString(R.string.URL);
        new DownloadTask().execute(URL_Conn);
    }
    private class DownloadTask extends AsyncTask<String,Void,NodeList> {


        @Override
        protected NodeList doInBackground(String... UrL) {
            try {

                URL url_call = new URL(UrL[0]);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                //Download the xml file
                Document document= db.parse(new InputSource(url_call.openStream()));
                document.getDocumentElement().normalize();
                //Locate the tag name
                mNodeList = document.getElementsByTagName("item");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return mNodeList;
        }

        @Override
        protected void onPostExecute(NodeList nodeList) {
            super.onPostExecute(nodeList);
            iDocument.getDoc(mNodeList);
            Log.i("TAG", "getNode: "+nodeList.getLength());
        }
    }
}
