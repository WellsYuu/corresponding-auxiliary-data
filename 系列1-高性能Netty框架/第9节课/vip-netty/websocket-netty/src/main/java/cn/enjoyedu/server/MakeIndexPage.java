package cn.enjoyedu.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程和VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：生成index页面的内容
 */
public final class MakeIndexPage {

    private static final String NEWLINE = "\r\n";

    public static ByteBuf getContent(String webSocketLocation) {
        return Unpooled.copiedBuffer(
                "<html><head><title>Web Socket Test</title></head>"
                        + NEWLINE +
                "<body>" + NEWLINE +
                "<script type=\"text/javascript\">" + NEWLINE +
                "var socket;" + NEWLINE +
                "if (!window.WebSocket) {" + NEWLINE +
                "  window.WebSocket = window.MozWebSocket;" + NEWLINE +
                '}' + NEWLINE +
                "if (window.WebSocket) {" + NEWLINE +
                "  socket = new WebSocket(\"" + webSocketLocation + "\");"
                        + NEWLINE +
                "  socket.onmessage = function(event) {" + NEWLINE +
                "    var ta = document.getElementById('responseText');"
                        + NEWLINE +
                "    ta.value = ta.value + '\\n' + event.data" + NEWLINE +
                "  };" + NEWLINE +
                "  socket.onopen = function(event) {" + NEWLINE +
                "    var ta = document.getElementById('responseText');"
                        + NEWLINE +
                "    ta.value = \"Web Socket opened!\";" + NEWLINE +
                "  };" + NEWLINE +
                "  socket.onclose = function(event) {" + NEWLINE +
                "    var ta = document.getElementById('responseText');"
                        + NEWLINE +
                "    ta.value = ta.value + \"Web Socket closed\"; "
                        + NEWLINE +
                "  };" + NEWLINE +
                "} else {" + NEWLINE +
                "  alert(\"Your browser does not support Web Socket.\");"
                        + NEWLINE +
                '}' + NEWLINE +
                NEWLINE +
                "function send(message) {" + NEWLINE +
                "  if (!window.WebSocket) { return; }" + NEWLINE +
                "  if (socket.readyState == WebSocket.OPEN) {" + NEWLINE +
                "    socket.send(message);" + NEWLINE +
                "  } else {" + NEWLINE +
                "    alert(\"The socket is not open.\");" + NEWLINE +
                "  }" + NEWLINE +
                '}' + NEWLINE +
                "</script>" + NEWLINE +
                "<form onsubmit=\"return false;\">" + NEWLINE +
                "<input type=\"text\" name=\"message\" " +
                        "value=\"Hello, World!\"/>" +
                "<input type=\"button\" value=\"Send Web Socket Data\""
                        + NEWLINE +
                "       onclick=\"send(this.form.message.value)\" />"
                        + NEWLINE +
                "<h3>Output</h3>" + NEWLINE +
                "<textarea id=\"responseText\" " +
                        "style=\"width:500px;height:300px;\"></textarea>"
                        + NEWLINE +
                "</form>" + NEWLINE +
                "</body>" + NEWLINE +
                "</html>" + NEWLINE, CharsetUtil.US_ASCII);
    }

}
