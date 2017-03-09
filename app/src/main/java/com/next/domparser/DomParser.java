package com.next.domparser;

import android.os.AsyncTask;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class DomParser extends AppCompatActivity implements IDocument,View.OnClickListener{
     TextView mTextView;
     Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView= (TextView) findViewById(R.id.textView);
        mButton= (Button) findViewById(R.id.get_data);
        mButton.setOnClickListener(this);
    }




    @Override
    public void getDoc(NodeList nodeList) {
        for (int i = 0; i <nodeList.getLength() ; i++) {
            Node node = nodeList.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                Log.i("TAG", "getNode: "+element);
                // Set the texts into TextViews from item nodes
                // Get the title
                mTextView.setText(mTextView.getText() + "Title :" +getNode("title",element) + "\n" + "\n");
                mTextView.setText(mTextView.getText()+"Description"+getNode("description",element) +"\n" + "\n");
                mTextView.setText(mTextView.getText()+"Link"+getNode("link",element)+"\n" + "\n");
                mTextView.setText(mTextView.getText()+"Date"+getNode("date",element)+"\n" + "\n");
            }
        }
    }

    private String getNode(String sTag , Element element) {
        NodeList nodeList= element.getElementsByTagName(sTag).item(0).getChildNodes();
         Node node = nodeList.item(0);
        Log.i("TAG", "getNode: "+node);
         return node.getNodeValue();

    }

    @Override
    public void onClick(View view) {
       DomController domController= new DomController(this,getApplicationContext());
        domController.getData();

    }
}
