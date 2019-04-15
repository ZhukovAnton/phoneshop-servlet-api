<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" class="com.es.phoneshop.model.cart.Cart" scope="request"/>
<jsp:useBean id="recentlyViewed" class="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Cart">
    <c:choose>
        <c:when test="${not empty errors}">
            <p style="color: firebrick">
                Error
            </p>
        </c:when>
        <c:otherwise>
            <p style="color: forestgreen">
                    ${param.message}
            </p>
        </c:otherwise>
    </c:choose>

    <form method="post">
        <table>
            <thead>
            <tr>
                <td>Image</td>
                <td>Description</td>
                <td class="price">Price</td>
                <td>Quantity</td>
                <td></td>
            </tr>
            </thead>
            <c:forEach items="${cart.cartItems}" var="item" varStatus="status">
                <tr>
                    <td>
                        <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${item.product.imageUrl}">
                    </td>
                    <td><a href="${pageContext.servletContext.contextPath}/products/${item.product.id}"> ${item.product.description} </a> </td>
                    <td class="price">
                        <fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="${item.product.currency.symbol}"/>
                    </td>
                    <td>
                        <input name="quantity" type="number" value="${not empty paramValues.quantity[status.index] ? paramValues.quantity[status.index] : item.quantity}"  style="text-align: right"/>
                        <input name="productId" type="hidden" value="${item.product.id}"/>
                        <c:if test="${not empty errors[status.index]}">
                            <br>
                            <span style="color: firebrick">
                                ${errors[status.index]}
                            </span>
                        </c:if>
                    </td>
                    <td>
                        <button formaction="${pageContext.servletContext.contextPath}/cart/delete/${item.product.id}">Delete</button>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="3" style="text-align: right">Total</td>
                <td colspan="2">$${cart.totalPrice}</td>
            </tr>
        </table>
        <br>
        <button>Update</button>
    </form>

    <tags:recentlyViewed recentlyViewed="${recentlyViewed}"/>
</tags:master>