<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" class="com.es.phoneshop.model.cartandcheckout.order.Order" scope="request"/>
<jsp:useBean id="recentlyViewed" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="delivery" class="com.es.phoneshop.model.delivery.DeliveryMode" scope="request"/>
<jsp:useBean id="nameError" class="java.lang.String" scope="request"/>
<jsp:useBean id="addressError" class="java.lang.String" scope="request"/>
<tags:master pageTitle="Checkout">
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>Description</td>
            <td class="price">Price</td>
            <td>Quantity</td>
        </tr>
        </thead>
        <c:forEach items="${order.orderItems}" var="item" varStatus="status">
            <tr>
                <td>
                    <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${item.product.imageUrl}">
                </td>
                <td><a href="${pageContext.servletContext.contextPath}/products/${item.product.id}"> ${item.product.description} </a> </td>
                <td class="price">
                    <fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="${item.product.currency.symbol}"/>
                </td>
                <td>
                        ${item.quantity}
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="3" style="text-align: right">Total</td>
            <td>$${order.totalPrice}</td>
        </tr>
    </table>
    <br>
    <form method="post" action="${pageContext.servletContext.contextPath}/checkout">
        <p>
            <label for="name">Name:</label>
            <input type="text" name="name" id="name"/>
            <c:if test="${not empty nameError}">
                <span style="color: firebrick">
                        ${nameError}
                </span>
            </c:if>
        </p>
        <p>
            <label for="address">Address:</label>
            <input type="text" name="address" id="address"/>
            <c:if test="${not empty addressError}">
                <span style="color: firebrick">
                        ${addressError}
                </span>
            </c:if>
        </p>
        <select name="delivery">
            <c:forEach items="${delivery.modes}" var="mode">
                <option>${mode}</option>
            </c:forEach>
        </select>
        <%--
        <p>
            Delivery Cost: ${selectedDelivery.deliveryCost}
        </p>
--%>
        <p>
            <button>
                Place Order
            </button>
        </p>
    </form>

    <tags:recentlyViewed recentlyViewed="${recentlyViewed}"/>
</tags:master>