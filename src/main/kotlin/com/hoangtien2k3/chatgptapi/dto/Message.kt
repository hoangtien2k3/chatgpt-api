package com.hoangtien2k3.chatgptapi.dto

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Data
@AllArgsConstructor
@NoArgsConstructor
data class Message(var role: String?, var content: String?)