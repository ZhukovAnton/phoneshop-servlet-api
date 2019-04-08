<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<jsp:useBean id="cart" type="java.util.ArrayList" scope="request"/>
<jsp:useBean id="recentlyViewed" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product Detail">
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>Description</td>
            <td class="price">Price</td>
        </tr>
        </thead>
        <tr>
            <td>
                <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
            </td>
            <td>${product.description}</td>
            <td class="price">
                <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
            </td>
        </tr>
    </table>
</tags:master>
<p>
    <form method="post">
        <input name="quantity" value="${!empty param.quantity ? param.quantity : 1}"/>
        <button>Add to Cart</button>
    </form>
</p>
<c:choose>
    <c:when test="${!empty error}">
        <p style="color: firebrick">
            ${error}
        </p>
    </c:when>
    <c:otherwise>
        <p style="color: forestgreen">
            ${param.message}
        </p>
    </c:otherwise>
</c:choose>
<br>
<jsp:include page="/minicart"/>
<br>
<tags:recentlyViewed recentlyViewed="${recentlyViewed}"/>