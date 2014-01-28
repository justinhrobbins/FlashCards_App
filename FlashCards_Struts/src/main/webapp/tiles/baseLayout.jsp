<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><tiles:insertAttribute name="title" ignore="true" /></title>

		<sj:head jquerytheme="redmond" loadFromGoogle="true" />
		
		<link rel="stylesheet" type="text/css" media="screen" href="../styles/style.css" />

		<!--  begin used by jQuery aehlke-tag-it -->
		<link rel="stylesheet" type="text/css" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1/themes/flick/jquery-ui.css" />
		<link rel="stylesheet" type="text/css" href="../css/jquery.tagit.css" />
		<link rel="stylesheet" type="text/css" href="../css/tagit.ui-zendesk.css" />
		<script type="text/javascript" src="../js/tag-it.js" charset="utf-8"></script>
		<!--  end used by jQuery aehlke-tag-it -->

	</head>
	<body>
		<div id="wrap">
			<div id="header">
				<tiles:insertAttribute name="header" />
			</div>
			<div id="content">
				<div class="right">
					<tiles:insertAttribute name="body" />
				</div>
				<div class="left">
					<tiles:insertAttribute name="menu" />
				</div>
				<div style="clear: both;" ></div>
			</div>
			<div id="footer">
				<tiles:insertAttribute name="footer" />
			</div>
		</div>
	</body>
</html>