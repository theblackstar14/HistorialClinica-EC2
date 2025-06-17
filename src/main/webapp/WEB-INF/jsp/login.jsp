<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Iniciar Sesión · Historias Clínicas</title>
  <!-- Tailwind CSS CDN -->
  <script src="https://cdn.tailwindcss.com"></script>
  <script>
    tailwind.config = { theme: { extend: { colors: { brand: '#2563eb' } } } }
  </script>
</head>
<body class="bg-gray-100 min-h-screen flex items-center justify-center">
  <div class="bg-white p-8 rounded-2xl shadow-lg w-full max-w-sm">
    <h1 class="text-3xl font-bold text-center text-brand mb-6">Historias Clínicas</h1>
    <h2 class="text-xl font-medium text-gray-700 mb-4 text-center">Iniciar Sesión</h2>

    <c:if test="${not empty msg}">
      <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
        ${msg}
      </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post" class="space-y-4">
      <div>
        <label class="block text-sm font-medium text-gray-600 mb-1" for="username">Usuario</label>
        <input id="username" name="username" type="text" required
               class="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-brand"/>
      </div>
      <div>
        <label class="block text-sm font-medium text-gray-600 mb-1" for="password">Contraseña</label>
        <input id="password" name="password" type="password" required
               class="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-brand"/>
      </div>
      <button type="submit"
              class="w-full bg-brand text-white font-semibold py-2 rounded-lg hover:bg-blue-700 transition">
        Entrar
      </button>
    </form>

  </div>
</body>
</html>
