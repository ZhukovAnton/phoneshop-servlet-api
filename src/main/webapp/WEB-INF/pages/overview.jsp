<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" class="com.es.phoneshop.model.cartandcheckout.order.Order" scope="request"/>
<tags:master pageTitle="Overview">
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
            <td colspan="2" style="text-align: right">Delivery</td>
            <td colspan="2"> $${order.deliveryMode.deliveryCost} </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: right">Total</td>
            <td colspan="2"> $${order.totalPrice}</td>
        </tr>
    </table>
    <br>
    <form method="post" action="/checkout">
        <p>
            Name:
            ${order.name}
        </p>
        <p>
            Address:
            ${order.address}
        </p>
        <p>
            Date:
                ${order.date}
        </p>
        <p>
            Payment method:
                ${order.paymentMethod}
        </p>
        <p>
            Delivery mode:
            ${order.deliveryMode}
        </p>


        <p>
            <span style="color: green">
                <b>
                    Your order will be processed soon. <br>
                    And now you can Go to Product List and look for anything interesting for you!
                </b>
            </span>

        </p>
        <button type="submit" formaction="${pageContext.servletContext.contextPath}/products" formmethod="get">
            GO!
        </button>
    </form>

</tags:master>