package me.zdziszkee.flatnotifier
package clients

import jakarta.mail.internet.MimeMessage
import jakarta.mail.{Authenticator, Message, PasswordAuthentication, Session, Transport}

import java.net.InetAddress
import java.time.LocalDateTime
import java.util.Properties

object EmailClient {

  def sendEmail(receiver: String, messageToSent: String): Unit = {
    val properties = new Properties()
    properties.put("mail.smtp.host", "smtp.gmail.com")
    properties.put("mail.smtp.port", "465")
    properties.put("mail.smtp.auth", "true")
    properties.put("mail.smtp.socketFactory.port", "465")
    properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
    val session = Session.getInstance(properties, new Authenticator {
      override def getPasswordAuthentication: PasswordAuthentication = new PasswordAuthentication("mieszkania091@gmail.com", "vyzzjzdnsdpircdf")
    })
    val message: MimeMessage = new MimeMessage(session)
    message.setFrom("mieszkania091@gmail.com")
    message.setRecipients(Message.RecipientType.TO, receiver)

    message.setSubject(s"${LocalDateTime.now()} Nowe oferty bambiku")

    // Set the content of the email
    message.setText(messageToSent)
    Transport.send(message)
    // If you want to send an HTML email, use setContent instead:
    // message.setContent("<html><body><h1>Hello</h1><p>This is an HTML email.</p></body></html>", "text/html");

    // Send the email

  }
}
