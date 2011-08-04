<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
    <title>Home</title>

    <style type="text/css">
        #sortable {
            list-style-type: none;
            margin: 0;
            padding: 0;
            width: 60%;
        }

        #sortable li {
            margin: 0 3px 3px 3px;
            padding: 0.4em;
            padding-left: 1.5em;
            font-size: 1.4em;
            height: 18px;
        }

        #sortable li span {
            position: absolute;
            margin-left: -1.3em;
        }
    </style>

    <script language="JavaScript" src="${request.contextPath}/resources/js/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
    <script language="JavaScript" src="${request.contextPath}/resources/js/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

</head>
<body>
<h1>
    Hello world!
</h1>

<P> The time on the server is ${serverTime}.
</P>


<script language="JavaScript">
    $(function() {
        $("#sortable").sortable();
        $("#sortable").disableSelection();
    });
</script>


<div class="todo">

    <ul id="sortable">
        <li class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>Item 1</li>
        <li class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>Item 2</li>
        <li class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>Item 3</li>
        <li class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>Item 4</li>
        <li class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>Item 5</li>
        <li class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>Item 6</li>
        <li class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>Item 7</li>
    </ul>

</div>


</body>
</html>
