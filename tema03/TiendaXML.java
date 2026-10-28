/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tema3;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *
 * @author ADMIN
 */
public class TiendaXML {
    
    public Document cargarXML() throws Exception {
        //creamos el puntero del archivo xml
        File archivo = new File ("tienda.xml");
        //creamos la factoría del lector de DOM
        DocumentBuilderFactory factory= DocumentBuilderFactory.newInstance();
        //creamos el objeto que va a leer el DOM
        DocumentBuilder builder = factory.newDocumentBuilder();
        //Java lee el XML y crea el árbol de DOM
        Document document = builder.parse(archivo);
        
        
        return document;
    }
    public NodeList obtenerNombres(Document document) throws Exception {
        //creamos la factoria para usar xpath
        XPath xpath = XPathFactory.newInstance().newXPath();
        String expresion = "/tienda/producto/nombre";
        NodeList nombres = (NodeList)xpath.evaluate(expresion,document,XPathConstants.NODESET);      
        return nombres;
    }
    
    
}
