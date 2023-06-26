package com.example.demo.utility;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Extrato {
    /*
    * Exemplo de utilização:
     	try {
			Extrato extrato = new Extrato("extrato.xml");
			extrato.importarDados();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    *
    * Os métodos getData, getDescricoes e getValores armazenam valores das colunas Data, Descricao e Valor do template de extrato escolhido.
    * Logo, para pegar os valores de uma linha, usa-se o mesmo índice em cada uma das colunas. Assim, para retornar os valores da primeira linha:
    *   datas.get(0); descricoes.get(0); valores.get(0);
    * */

    private File arquivo;
    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    private Document document;
    private List<String> datas = new ArrayList<>();
    private List<String> descricoes = new ArrayList<>();
    private List<String> valores = new ArrayList<>();

    public Extrato(String arq) throws Exception {
        if (!arq.endsWith(".xml")) {
            throw new FileNotFoundException("Erro ao tentar importar extrato: o arquivo '" + arq + "' não possui extensão XML...");
        }
        arquivo = new File(arq);
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        document = builder.parse(arquivo);
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
