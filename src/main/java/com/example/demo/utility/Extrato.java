package com.example.demo.utility;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Extrato {
    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    private Document document;
    private List<String> datas = new ArrayList<>();
    private List<String> descricoes = new ArrayList<>();
    private List<String> valores = new ArrayList<>();

    public Extrato(String arq) throws Exception {
        InputStream stream = new ByteArrayInputStream(arq.getBytes(StandardCharsets.UTF_8));
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        document = builder.parse(stream);
        document.getDocumentElement().normalize();
    }

    public void importarDados() {
        Element root = document.getDocumentElement();
        NodeList nodeList = root.getElementsByTagName("transacao");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                datas.add(element.getElementsByTagName("data").item(0).getTextContent());
                descricoes.add(element.getElementsByTagName("descricao").item(0).getTextContent());
                valores.add(element.getElementsByTagName("valor").item(0).getTextContent());
            }

        }
    }

    public List<String> getDatas() {
        return datas;
    }

    public List<String> getDescricoes() {
        return descricoes;
    }

    public List<String> getValores() {
        return valores;
    }
}
