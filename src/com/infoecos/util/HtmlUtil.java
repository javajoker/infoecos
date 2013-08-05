package com.infoecos.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * HTML utilities
 * 
 * htmlparser required, http://htmlparser.sourceforge.net/
 * 
 * @author Ning Hu
 * 
 */
public class HtmlUtil {
	private static Pattern ptnScript = Pattern.compile(
			"(\\<script\\b.*?\\>)(.*?)(\\</script\\s*\\>)",
			Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	/**
	 * Get HTML root element as node list
	 * 
	 * @param html
	 * @return
	 */
	public static NodeList getHtmlRoot(String html) {
		Parser parser = new Parser(new Lexer(html));
		try {
			parser.setEncoding("UTF-8");
			return parser.parse(null);
		} catch (ParserException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	private static long SCRIPT_ID = 0l;

	/**
	 * Get HTML root element as node list, save script segments in given map
	 * collection
	 * 
	 * @param html
	 * @param scriptSegs
	 *            script segments will be stored here, a script place holder
	 *            will be saved in node list
	 * @return
	 */
	public static NodeList getHtmlRoot(String html,
			Map<String, String> scriptSegs) {
		Matcher m = ptnScript.matcher(html);
		StringBuilder sbHtml = new StringBuilder();
		int idx = 0;
		while (m.find(idx)) {
			++SCRIPT_ID;
			sbHtml.append(html.substring(idx, m.start()));
			sbHtml.append(m.group(1));
			String id = String.format("___SCRIPT_%d_TPIRCS___", SCRIPT_ID);
			sbHtml.append(id);
			scriptSegs.put(id, m.group(2));
			sbHtml.append(m.group(3));
			idx = m.end();
		}
		sbHtml.append(html.substring(idx));
		html = sbHtml.toString();

		return getHtmlRoot(html);
	}

	/**
	 * Get HTML string of specified node list
	 * 
	 * @param nl
	 * @param scriptSegs
	 * @return
	 */
	public static String toHtml(NodeList nl, Map<String, String> scriptSegs) {
		String html = nl.toHtml();
		for (String key : scriptSegs.keySet()) {
			html = html.replace(key, scriptSegs.get(key));
		}
		return html;
	}

	/**
	 * Get HTML string of a node
	 * 
	 * @param node
	 * @param scriptSegs
	 * @return
	 */
	public static String toHtml(Node node, Map<String, String> scriptSegs) {
		String html = node.toHtml();
		for (String key : scriptSegs.keySet()) {
			html = html.replace(key, scriptSegs.get(key));
		}
		return html;
	}

	/**
	 * Get valid HTML, auto close HTML tags if necessary
	 * 
	 * @param html
	 * @return
	 */
	public static String getValidHtml(String html) {
		try {
			NodeList nodes = getHtmlRoot(html);
			StringBuilder newHtml = new StringBuilder();
			for (Node n : nodes.toNodeArray()) {
				if (n instanceof TagNode) {
					TagNode tn = (TagNode) n;
					if (tn.isEndTag())
						continue;
				}
				newHtml.append(n.toHtml());
			}
			return newHtml.toString();
		} catch (Exception e) {
			// do not through exceptions, just return input html
			return html;
		}
	}

	/**
	 * Get node list with certain tag name
	 * 
	 * @param list
	 *            parent node list
	 * @param tagName
	 *            tag name
	 * @return
	 * @throws Exception
	 */
	public static Node[] getNodes(NodeList list, String tagName)
			throws Exception {
		List<Node> nodes = new ArrayList<Node>();
		getNodes(list, tagName, null, null, nodes);
		return (Node[]) nodes.toArray(new Node[nodes.size()]);
	}

	/**
	 * Get sub node list of specified node list, given tag name, attribute,
	 * value if necessary
	 * 
	 * @param list
	 *            parent node list
	 * @param tagName
	 *            tag name
	 * @param attribute
	 *            attribute
	 * @param value
	 *            attribute value
	 * @return
	 * @throws Exception
	 */
	public static Node[] getNodes(NodeList list, String tagName,
			String attribute, String value) throws Exception {
		List<Node> nodes = new ArrayList<Node>();
		getNodes(list, tagName, attribute, value, nodes);
		return (Node[]) nodes.toArray(new Node[nodes.size()]);
	}

	private static void getNodes(NodeList list, String tagName,
			String attribute, String value, List<Node> nodes) throws Exception {
		if (list == null)
			return;
		for (Node n : list.toNodeArray()) {
			boolean added = false;
			if (n instanceof TagNode) {
				TagNode node = (TagNode) n;
				if (node.getTagName().equalsIgnoreCase(tagName)) {
					if (attribute == null || "".equals(attribute)) {
						nodes.add(node);
						added = true;
					} else {
						String avs = node.getAttribute(attribute);
						if (avs == null)
							continue;
						for (String v : avs.split("\\s+")) {
							if (v.equalsIgnoreCase(value)) {
								nodes.add(node);
								added = true;
								break;
							}
						}
					}
				}
			}
			if (!added)
				getNodes(n.getChildren(), tagName, attribute, value, nodes);
		}
	}

	/**
	 * Get inner text of certain HTML string
	 * 
	 * @param html
	 * @return
	 */
	public static String getInnerText(String html) {
		return getInnerText(html, null);
	}

	/**
	 * Get inner text of certain HTML string
	 * 
	 * @param html
	 * @param leaveBlank
	 * @return
	 */
	public static String getInnerText(String html, boolean leaveBlank) {
		return getInnerText(html, null, leaveBlank);
	}

	/**
	 * Get inner text of certain HTML string, given node filter tagname,
	 * attribute, attr value, replace inner text if necessary
	 * 
	 * @param html
	 * @param filterNodeFormat
	 *            { tagname, attribute, attr value, inside replacement match,
	 *            inside replacement}
	 * @return
	 */
	public static String getInnerText(String html, String[][] filterNodeFormat) {
		return getInnerText(html, filterNodeFormat, false);
	}

	/**
	 * Get inner text of certain HTML string, given node filter tagname,
	 * attribute, attr value, replace inner text if necessary
	 * 
	 * @param html
	 * @param filterNodeFormat
	 *            { tagname, attribute, attr value, inside replacement match,
	 *            inside replacement}
	 * @param leaveBlank
	 *            keep blank characters
	 * @return
	 */
	public static String getInnerText(String html, String[][] filterNodeFormat,
			boolean leaveBlank) {
		NodeList nl = HtmlUtil.getHtmlRoot(String.format("<htm>%s</htm>",
				HtmlUtil.getValidHtml(HtmlUtil.filterOutScriptSegment(html))));
		StringBuilder txt = new StringBuilder();
		for (Node n : nl.toNodeArray()) {
			txt.append(getInnerText(n, filterNodeFormat, leaveBlank));
		}
		return txt.toString();
	}

	/**
	 * Get inner text of certain HTML node
	 * 
	 * @param node
	 * @return
	 */
	public static String getInnerText(Node node) {
		return getInnerText(node, null);
	}

	/**
	 * Get inner text of certain HTML node
	 * 
	 * @param node
	 * @param leaveBlank
	 *            keep blank characters
	 * @return
	 */
	public static String getInnerText(Node node, boolean leaveBlank) {
		return getInnerText(node, null, leaveBlank);
	}

	/**
	 * Get inner text of certain HTML node, given node filter tagname,
	 * attribute, attr value, replace inner text if necessary
	 * 
	 * @param node
	 * @param filterNodeFormat
	 *            { tagname, attribute, attr value, inside replacement match,
	 *            inside replacement}
	 * @return
	 */
	public static String getInnerText(Node node, String[][] filterNodeFormat) {
		return getInnerText(node, filterNodeFormat, false);
	}

	/**
	 * Get inner text of certain HTML node list, given node filter tagname,
	 * attribute, attr value, replace inner text if necessary
	 * 
	 * @param node
	 * @param filterNodeFormat
	 *            { tagname, attribute, attr value, inside replacement match,
	 *            inside replacement}
	 * @param leaveBlank
	 *            keep blank characters
	 * @return
	 */
	public static String getInnerText(Node node, String[][] filterNodeFormat,
			boolean leaveBlank) {
		NodeList nl = node.getChildren();
		if (nl == null)
			return "";

		StringBuilder sb = new StringBuilder();
		try {
			for (Node n : nl.toNodeArray()) {
				if (n instanceof TextNode) {
					TextNode tn = (TextNode) n;
					sb.append(StringEscapeUtils.unescapeHtml4(tn.getText()));
					continue;
				} else if (!(n instanceof TagNode))
					continue;

				TagNode tn = (TagNode) n;
				if (filterNodeFormat == null) {
					sb.append(getInnerText(tn, filterNodeFormat, leaveBlank));
				} else {
					for (String[] tag : filterNodeFormat) {
						if (!tag[0].equalsIgnoreCase(tn.getTagName()))
							continue;
						if (tag[1] == null) {
							sb.append(getInnerText(tn, filterNodeFormat,
									leaveBlank).replaceAll(tag[3], tag[4]));
							break;
						}
						String avs = tn.getAttribute(tag[1]);
						if (avs == null)
							continue;
						for (String v : avs.split("\\s+")) {
							if (v.equalsIgnoreCase(tag[2])) {
								sb.append(getInnerText(tn, filterNodeFormat,
										leaveBlank).replaceAll(tag[3], tag[4]));
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// do not through exceptions, just return input html
		}
		if (leaveBlank)
			return sb.toString().replaceAll("(\\s) ", "$1");
		else
			return sb.toString().replaceAll("\\s+", " ");
	}

	/**
	 * Filter out script/link/style/comment/... segment of html
	 * 
	 * @param html
	 * @return
	 */
	public static String filterOutScriptSegment(String html) {
		return html.replaceAll("(?i:<link\\b.*?/>)", "")
				.replaceAll("(?i:<script\\b.*?>.*?</script>)", "")
				.replaceAll("(?i:<style\\b.*?>.*?</style>)", "")
				.replaceAll("<\\s*(/?[\\w-]+).*?>", "<$1>")
				.replaceAll("<!--.*?-->", "");
	}
}
