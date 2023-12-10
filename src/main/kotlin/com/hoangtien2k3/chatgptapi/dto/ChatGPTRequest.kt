package com.hoangtien2k3.chatgptapi.dto

data class ChatGPTRequest(val model: String, val messages: List<Message>) {
    constructor(model: String, prompt: String) : this(model, listOf(Message("user", prompt)))
}