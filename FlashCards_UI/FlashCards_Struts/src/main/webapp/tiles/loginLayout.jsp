<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><tiles:insertAttribute name="title" ignore="true" /></title>
		<link rel="stylesheet" type="text/css" media="screen" href="../styles/style.css" />
		<sj:head jquerytheme="redmond" loadFromGoogle="true" />
		<!-- Simple OpenID Selector -->
		<link type="text/css" rel="stylesheet" href="../css/openid.css" />

		<script type="text/javascript" src="../js/openid-jquery.js"></script>
		<script type="text/javascript" src="../js/openid-en.js"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				openid.init('openid_identifier');
				//openid.setDemoMode(true); //Stops form submission for client javascript-only test purposes
			});
		</script>
		<!-- /Simple OpenID Selector -->
		<style type="text/css">
			/* Basic page formatting */
			body {
				font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
			}
		</style>
	</head>
	<body>
		<div id="wrap">
			<div id="header">
				<tiles:insertAttribute name="header" />
			</div>
			<div id="content">
				<div class="center">
					<tiles:insertAttribute name="body" />
				</div>
				<div style="clear: both;" ></div>
			</div>
			<div id="footer">
				<tiles:insertAttribute name="footer" />
			</div>
		</div>
	</body>
</html>