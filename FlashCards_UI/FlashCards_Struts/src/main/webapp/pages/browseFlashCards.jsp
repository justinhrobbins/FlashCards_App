<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:actionmessage theme="jquery"/>

<script type='text/javascript'>
jQuery(document).ready(function () {
	$("a.toggler").click(function() {
		$(this.hash).slideToggle();
	});
    $('.slideshow').cycle({
        fx:     'fade',
        speed:  'fast',
        timeout: 0,
        next:   '#next2',
        prev:   '#prev2'
	});
 });
</script>

<table border="0px">
	<tbody>
		<tr>
			<td width="75%">
				<s:if test="flashCardList.size() > 0">

					<s:url var="listurl" action="browse">
						<s:param name="viewType">list</s:param>
					</s:url>
					<s:url var="slideshowurl" action="browse">
						<s:param name="viewType">slideshow</s:param>
					</s:url>

					<s:if test="viewType == 'slideshow'">
						<s:a href="%{listurl}"><s:text name="label.flashcard.listview" /></s:a>
						&nbsp;|&nbsp;
						<s:text name="label.flashcard.slideshow" />
					</s:if>
					<s:else>
						<s:text name="label.flashcard.listview" />
						&nbsp;|&nbsp;
						<s:a href="%{slideshowurl}"><s:text name="label.flashcard.slideshow" /></s:a>
					</s:else>

					<s:if test="viewType == 'slideshow'">

					</s:if>
					<s:else>
						<table width="100%" border="0px">
							<thead>
								<tr>
									<th><s:text name="label.flashcard.question" /></th>
								<tr>
							</thead>
							<tbody>
								<s:iterator value="flashCardList" status="status">
									<tr>
										<td class="wrapping">
											<s:a id="question%{#status.count}" cssClass="toggler" href="#answer%{#status.count}" ><s:property value="question" /></s:a>
											<s:div id="answer%{#status.count}" style="display:none;">
												<s:property value="answer" escape="false" />
												<s:if test="links.size() > 0">
													<s:iterator value="links" status="rowstatus" var="idx" >
														<a href="<s:property/>" target="_blank"><s:property/></a>
														<br/>
													</s:iterator>
												</s:if>
											</s:div>
										</td>
									</tr>
								</s:iterator>
							</tbody>				
						</table>
					</s:else>
				</s:if>
				<s:else>
					<br/>
					<s:text name="label.no.flashcards" />
				</s:else>
			</td>
			<td width="25%">
				<s:if test="tagList.size() > 0">
					<table width="100%" border="0px">
						<thead>
							<tr>
								<th><s:text name="label.tag.filter" /></th>
							<tr>
						</thead>
						<tbody>
							<tr>
								<td>
									<img src="../images/tag_yellow.gif">
									<s:if test="explodedTags == null">
										<s:text name="label.all.tags" />
									</s:if>
									<s:elseif  test="explodedTags == 'All'">
										<span class="selected"><s:text name="label.all.tags" /></span>
									</s:elseif>
									<s:else>
										<s:url var="url" action="browse" >
											<s:param name="explodedTags">All</s:param>
										</s:url>
										<s:a href="%{url}"><s:text name="label.all.tags" /></s:a>
									</s:else>
								</td>
							</tr>
							<s:iterator value="tagList" var="currentTag">
								<tr>
									<td>
										<s:if test="#currentTag.flashcards.size() > 0">
											<img src="../images/tag_yellow.gif">
											<s:if test="#currentTag.name == explodedTags">
												<span class="selected"><s:property value="name" /></span>
											</s:if>
											<s:else>
												<s:url var="url" action="browse">
													<s:param name="explodedTags">
														<s:property value="name" />
													</s:param>
												</s:url>
												<s:a href="%{url}"><s:property value="name" /></s:a>
											</s:else>
										</s:if>
										<s:else>
											<img src="../images/tag_yellow.gif">
												<s:property value="name" />
										</s:else>
									</td>
								</tr>
							</s:iterator>
						</tbody>				
					</table>
				</s:if>			
			</td>
		</tr>
	</tbody>
</table>

