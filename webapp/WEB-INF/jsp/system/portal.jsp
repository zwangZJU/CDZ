<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/jsp/common/tags.jsp"%>
<%

String path=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();

%>
<aos:html title="首页" base="http" lib="ext">
<body>

<img style="position:absolute; top:0px;left:0px; width:100% ;height:100%" src="<%=path%>/zhaf/static/image/zhihui.jpg">
</body>
</aos:html>

