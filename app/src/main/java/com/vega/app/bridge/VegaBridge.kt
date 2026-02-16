@JavascriptInterface
fun login(email: String, password: String) {

    CoroutineScope(Dispatchers.IO).launch {
        val success = SupabaseManager.login(email, password)

        webView.post {
            webView.evaluateJavascript(
                "window.onNativeLogin($success)",
                null
            )
        }
    }
}
