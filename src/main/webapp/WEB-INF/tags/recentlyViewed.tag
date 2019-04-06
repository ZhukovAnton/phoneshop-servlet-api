<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="recentlyViewed" type="java.util.ArrayList"%>


<table>
    <br>
    <thead>
    <b>
        Recently Viewed Phones
    </b>
    </thead>
    <c:forEach var="item" items="${recentlyViewed}">
        <td>
            <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${item.imageUrl}">
            <br><a href="/phoneshop-servlet-api/products/${item.id} "> ${item.description}</a>
            <br><fmt:formatNumber value="${item.price}" type="currency" currencySymbol="${item.currency.symbol}"/>
        </td>
    </c:forEach>
</table>