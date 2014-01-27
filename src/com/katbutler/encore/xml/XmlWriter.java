package com.katbutler.encore.xml;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;


public class XmlWriter<T> {
	
	private Class<T> klass;
	private String ns = null;
	private String listRootTag;
	private String rootTag;
	
	public XmlWriter(Class<T> klass) {
		this.klass = klass;
	}
	
	/**
	 * Write an XML with an {@link XmlDocument}
	 * 
	 * @param item {@link XmlDocument} annotated class
	 * @return the string representation of this {@link XmlDocument}
	 */
	public String writeXml(T item) {
		XmlDocument xmlDocument = klass.getAnnotation(XmlDocument.class);
		if(xmlDocument != null) {
			StringBuilder sb = new StringBuilder();
			setNs(xmlDocument.namespace().equals("") ? null : xmlDocument.namespace()); // Does the namespace need to be null or is "" ok?
			setListRootTag(xmlDocument.listRootName());
			setRootTag(xmlDocument.rootName());
			
			sb.append("<" + xmlDocument.rootName() + ">\n");
			
			for (Field field : klass.getDeclaredFields()) {
				// Need the annotation to check the tag name
				XmlElement xmlElement = field.getAnnotation(XmlElement.class);
				if (xmlElement != null) {
					try {
						field.setAccessible(true);
						if (field.get(item) != null) {
							sb.append("  <" + xmlElement.name() + ">");
							sb.append(field.get(item));
							sb.append("</" + xmlElement.name() + ">\n");
						}
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} finally {
						
					}
				}
			}
			
			sb.append("</" + xmlDocument.rootName() + ">\n");
			return sb.toString();
		}
		
		return null;
	}

	
	/**
	 * Write an Array of {@link XmlDocument} to a String XML
	 * @param itemList
	 * @return
	 */
	public String writeArrayXml(Collection<T> itemList) {
		XmlDocument xmlDocument = klass.getAnnotation(XmlDocument.class);
		if(xmlDocument != null) {
			StringBuilder sb = new StringBuilder();
			setNs(xmlDocument.namespace().equals("") ? null : xmlDocument.namespace()); // Does the namespace need to be null or is "" ok?
			setListRootTag(xmlDocument.listRootName());
			setRootTag(xmlDocument.rootName());
			
			sb.append("<" + xmlDocument.listRootName() + ">\n");
			
			for (T item : itemList) {
				sb.append(writeXml(item));
			}
			
			sb.append("</" + xmlDocument.listRootName() + ">\n");
			
			return sb.toString();
		}
		
		return null;
	}

	public String getListRootTag() {
		return listRootTag;
	}

	public void setListRootTag(String listRootTag) {
		this.listRootTag = listRootTag;
	}
	
	public String getNs() {
		return ns;
	}

	public void setNs(String ns) {
		this.ns = ns;
	}

	public String getRootTag() {
		return rootTag;
	}

	public void setRootTag(String rootTag) {
		this.rootTag = rootTag;
	}
}
