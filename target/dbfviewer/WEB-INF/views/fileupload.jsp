<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean"  prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html"  prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Upload Example</title>
</head>
<body>
	<h1>
		<bean:message key="label.common.title" bundle="commonMsg"/>
	</h1>

	<html:messages id="err_name" property="common.file.err" bundle="commonMsg">
		<div style="color: red">
			<bean:write name="err_name" />
		</div>
	</html:messages>
	<html:messages id="err_name" property="common.file.err.ext" bundle="commonMsg">
		<div style="color: red">
			<bean:write name="err_name" />
		</div>
	</html:messages>
	<html:messages id="err_name" property="common.file.err.size" bundle="commonMsg">
		<div style="color: red">
			<bean:write name="err_name" />
		</div>
	</html:messages>

	<html:form action="/Upload" method="post" enctype="multipart/form-data">
		<br />
		<bean:message key="label.common.file.label" bundle="commonMsg"/> : 
			<html:file property="file" size="50" />
		<br />
		<br />
		<html:submit>
			<bean:message key="label.common.button.submit" bundle="commonMsg"/>
		</html:submit>
	</html:form>
</body>
</html>