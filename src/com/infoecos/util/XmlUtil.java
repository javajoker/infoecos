package com.infoecos.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.StringEscapeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Common Xml utilities
 * 
 * @author Ning Hu
 * 
 */
public class XmlUtil {
	/**
	 * Get xml document, by stream
	 * 
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public static Document getDocument(InputStream input) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(input);
		return doc;
	}

	/**
	 * Get xml document, by xml string
	 * 
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public static Document getDocument(String xml) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes()));
		return doc;
	}

	/**
	 * Get xpath expression object by xpath string
	 * 
	 * @param xpathStr
	 * @return
	 * @throws Exception
	 */
	public static XPathExpression getXPathExpression(String xpathStr)
			throws Exception {
		return XPathFactory.newInstance().newXPath().compile(xpathStr);
	}

	/**
	 * Get xml nodes by xpath expression
	 * 
	 * @param doc
	 * @param expr
	 * @return
	 * @throws Exception
	 */
	public static NodeList getNodesByXPath(Document doc, XPathExpression expr)
			throws Exception {
		return (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
	}

	/**
	 * Get xml nodes by xpath string, based on xml document
	 * 
	 * @param doc
	 * @param xpath
	 * @return
	 * @throws Exception
	 */
	public static NodeList getNodesByXPath(Document doc, String xpath)
			throws Exception {
		return (NodeList) getXPathExpression(xpath).evaluate(doc,
				XPathConstants.NODESET);
	}

	/**
	 * Get xml nodes by xpath string, based on xml stream
	 * 
	 * @param input
	 * @param xpath
	 * @return
	 * @throws Exception
	 */
	public static NodeList getNodesByXPath(InputStream input, String xpath)
			throws Exception {
		return (NodeList) getXPathExpression(xpath).evaluate(
				getDocument(input), XPathConstants.NODESET);
	}

	/**
	 * Get xml nodes by xpath string, based on xml string
	 * 
	 * @param xml
	 * @param xpath
	 * @return
	 * @throws Exception
	 */
	public static NodeList getNodesByXPath(String xml, String xpath)
			throws Exception {
		return (NodeList) getXPathExpression(xpath).evaluate(getDocument(xml),
				XPathConstants.NODESET);
	}

	/**
	 * Get xml nodes by xpath expression, based on node
	 * 
	 * @param node
	 * @param xpath
	 * @return
	 * @throws Exception
	 */
	public static NodeList getNodesByXPath(Node node, XPathExpression xpath)
			throws Exception {
		return (NodeList) xpath.evaluate(node, XPathConstants.NODESET);
	}

	/**
	 * Get xml nodes by xpath string , based on node
	 * 
	 * @param node
	 * @param xpath
	 * @return
	 * @throws Exception
	 */
	public static NodeList getNodesByXPath(Node node, String xpath)
			throws Exception {
		return (NodeList) getXPathExpression(xpath).evaluate(node,
				XPathConstants.NODESET);
	}

	/**
	 * Get standard xml string of node
	 * 
	 * @param node
	 * @return
	 */
	public static String nodeToString(Node node) {
		StringWriter sw = new StringWriter();
		try {
			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.transform(new DOMSource(node), new StreamResult(sw));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return sw.toString();
	}

	/**
	 * Get standard xml string of nodes
	 * 
	 * @param nl
	 * @return
	 */
	public static String nodeToString(NodeList nl) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < nl.getLength(); i++) {
			Node n = nl.item(i);
			s.append(nodeToString(n));
		}
		return s.toString();
	}

	/**
	 * Get text of node
	 * 
	 * @param n
	 * @return
	 * @throws Exception
	 */
	public static String getText(Node n) throws Exception {
		StringBuilder s = new StringBuilder();
		getText(n, s);
		return s.toString().replaceAll("\\s+", " ");
	}

	/**
	 * Get nodes text
	 * 
	 * @param nl
	 * @return
	 * @throws Exception
	 */
	public static String[] getTexts(NodeList nl) throws Exception {
		List<String> txts = new ArrayList<String>();
		for (int i = 0; i < nl.getLength(); ++i) {
			String txt = getText(nl.item(i));
			if (!txt.trim().isEmpty())
				txts.add(txt);
		}
		return (String[]) txts.toArray(new String[txts.size()]);
	}

	private static void getText(Node n, StringBuilder s) throws Exception {
		String nv = n.getNodeValue();
		if (null != nv) {
			s.append(StringEscapeUtils.unescapeHtml4(nv));
			return;
		}

		NodeList nlA = n.getChildNodes();
		for (int i = 0; i < nlA.getLength(); ++i) {
			getText(nlA.item(i), s);
		}
	}
}
