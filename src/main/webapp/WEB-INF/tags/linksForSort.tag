<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="search" required="true" %>
<%@ attribute name="sort" required ="true"%>
<a href="?search=${search}&sort=${sort}&order=desc">desc</a>
<a href="?search=${search}&sort=${sort}&order=asc">asc</a>
