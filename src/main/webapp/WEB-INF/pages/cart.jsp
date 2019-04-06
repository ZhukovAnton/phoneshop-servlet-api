<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" class="com.es.phoneshop.model.cart.Cart" scope="request"/>
<jsp:useBean id="recentlyViewed" class="java.util.ArrayList" scope="request"/>

<tags:master pageTitle="Cart"/>

<table>
    <thead>
    <tr>
        <td>Image</td>
        <td>Description</td>
        <td class="price">Price</td>
        <td>Quantity</td>
    </tr>
    </thead>
    <c:forEach items="${cart.cartItems}" var="item">
            <tr>
                <td>
                    <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${item.product.imageUrl}">
                </td>
                <td>${item.product.description}</td>
                <td class="price">
                    <fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="${item.product.currency.symbol}"/>
                </td>
                <td>
                    ${item.quantity}
                </td>
            </tr>
    </c:forEach>
</table>

<tags:recentlyViewed recentlyViewed="${recentlyViewed}"/>
