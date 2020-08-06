package com.esmaeel.catchathief.data.models

data class BotMessagesResponse(
    val ok: Boolean? = false,
    val result: List<Result?>? = listOf()
) {
    data class Result(
        val message: Message? = Message(),
        val update_id: Int? = 0
    ) {
        data class Message(
            val chat: Chat? = Chat(),
            val date: Int? = 0,
            val from: From? = From(),
            val message_id: Int? = 0,
            val text: String? = ""
        ) {
            data class Chat(
                val first_name: String? = "",
                val id: Int? = 0,
                val last_name: String? = "",
                val type: String? = ""
            )

            data class From(
                val first_name: String? = "",
                val id: Int? = 0,
                val is_bot: Boolean? = false,
                val language_code: String? = "",
                val last_name: String? = ""
            )
        }
    }
}

