<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean"  prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<tiles:insert definition="dict-base-layout" >
	<tiles:put name="body" value="/WEB-INF/parts/body.jsp" />
	<tiles:put name="uploader" value="/WEB-INF/parts/upload.jsp"/>
</tiles:insert>