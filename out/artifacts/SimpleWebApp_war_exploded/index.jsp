<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://xmlns.jcp.org/jsf/html"
      xmlns:p="http://primefaces.org/ui">

<h:head>
    <title>Добро пожаловать</title>
</h:head>

<h:body>
    <h:form>
        <p:panel header="Добро пожаловать!" style="max-width:400px; margin:auto; text-align:center;">
            <h:outputText value="Вы успешно зарегистрировались!" />
            <br/><br/>
            <h:link value="Вернуться на главную" outcome="register"/>
        </p:panel>
    </h:form>
</h:body>
</html>
