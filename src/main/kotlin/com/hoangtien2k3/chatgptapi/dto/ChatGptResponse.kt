package com.hoangtien2k3.chatgptapi.dto

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Data
@AllArgsConstructor
@NoArgsConstructor
data class ChatGptResponse(val choices: List<Choice>) {

    @NoArgsConstructor
    data class Choice(val index: Int, val message: Message)

}