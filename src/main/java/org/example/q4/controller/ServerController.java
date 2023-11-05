package org.example.q4.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.example.q4.model.Candidato;

import java.io.IOException;
import java.net.*;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.*;

public class ServerController {
    private Map<Integer, Candidato> candidatos = new HashMap<>();
    private Date timer = new Date();

    public ServerController() {
    }

    public void runServer() {
        receberCandidatos();
        setTimer();

        try {
            InetAddress addr = InetAddress.getByName("239.0.0.1");
            DatagramSocket ds = new DatagramSocket();

            String xml = candidatosToXml();

            // TODO: fazer envio de candidatos por TCP em unicast, perguntar para que usar o multicast se os candidatos vão ser enviados em unicast como pede no trabalho
            byte[] b = xml.getBytes();
            DatagramPacket pkg = new DatagramPacket(b, b.length, addr, 12347);
            System.out.println(xml);

            ds.send(pkg);
            ds.close();
            registerVote();
        } catch (Exception e) {
            System.out.println("Nao foi possivel enviar a mensagem");
        }
    }

    public void sendResult() {
        try {
            InetAddress addr = InetAddress.getByName("239.0.0.1");
            DatagramSocket ds = new DatagramSocket();

            String result = getVencedor();
            result = "[RESULTADO] " + result + ";";
            System.out.println(result);

            byte[] b = result.getBytes();
            DatagramPacket pkg = new DatagramPacket(b, b.length, addr, 12347);

            ds.send(pkg);
            ds.close();
        } catch (Exception e) {
            System.out.println("Nao foi possivel enviar a mensagem dos candidatos vencedores");
        }
    }

    public void sendMessage() {
        try {
            InetAddress addr = InetAddress.getByName("239.0.0.1");
            DatagramSocket ds = new DatagramSocket();

            Scanner scanner = new Scanner(System.in);

            String message = scanner.nextLine();
            message = "[MESSAGE] " + message + ";";

            byte[] b = message.getBytes();
            DatagramPacket pkg = new DatagramPacket(b, b.length, addr, 12347);

            ds.send(pkg);
            ds.close();
        } catch (Exception e) {
            System.out.println("Nao foi possivel enviar a mensagem aos votantes");
        }
    }

    private String getVencedor() {
        List<Candidato> candidatoList = new ArrayList<>(this.candidatos.values());
        int totalVotos = candidatoList.get(0).getVotos();
        Candidato vencedor = candidatoList.get(0);

        for (int i = 1; i < candidatoList.size(); i++) {
            totalVotos += candidatoList.get(i).getVotos();

            if (candidatoList.get(i).getVotos() > vencedor.getVotos()) {
                vencedor = candidatoList.get(i);
            }
        }

        if (totalVotos == 0) {
            return "Nenhum voto foi realizado";
        }

        int porcentagem = vencedor.getVotos() / totalVotos;

        return "O candidato vencedor foi o " + vencedor.getNome() + " com " + new DecimalFormat("#.##%").format(porcentagem) + " dos votos";

    }

    private void receberCandidatos() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("====== REGISTRAR CANDIDATO ======");
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("Número: ");
            int numero = scanner.nextInt();

            this.candidatos.put(numero, new Candidato(nome, numero));

            scanner.nextLine();
            String novoCandidato = scanner.nextLine();
            if (novoCandidato.equals("n") || novoCandidato.equals("N")) {
                break;
            }
        }
    }

    public String setCandidatos(String xmlCandidatos) {
        System.out.println("Fazendo candidatos");
        List<Candidato> candidatoList = XMLToArray(xmlCandidatos);

        for (Candidato c : candidatoList) {
            this.candidatos.put(c.getNumero(), c);
        }
        System.out.println("candidatos feitos");

        return "Candidatos cadastrados com sucesso";
    }

    private void setTimer() {
        // define o tempo de votação
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o horário de encerramento da votação: ");
        String[] time = scanner.nextLine().split(":");

        // Crie um objeto Calendar
        Calendar calendar = Calendar.getInstance();

        // Defina as horas, minutos e segundos desejados
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(time[1]));
        calendar.set(Calendar.SECOND, Integer.parseInt(time[2]));

        // Obtenha um objeto Date a partir do Calendar
        this.timer = calendar.getTime();
        Cronometro cronometro = new Cronometro(timer, this);
    }

    private void registerVote() {
        Mensagem m = new Mensagem(this);

        try{
            System.out.println("Aguardando conexão...");
            ServerSocket listenSocket = new ServerSocket(12348);

            while (true) {
                Socket clientSocket = listenSocket.accept();

                System.out.println("Conexão estabelecida com: " + clientSocket.getInetAddress().getHostAddress());
                Connection c = new Connection(clientSocket, this);
            }

        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        }
    }

    public String vote(int numeroCandidato) {
        var candidato = candidatos.get(numeroCandidato);
        Date currentTime = new Date();

        if (candidato != null) {
            if (currentTime.after(this.timer)) {
                return "timer expirou";
            }

            candidato.vote();
            return candidato.getNome();
        }
        return "falha";
    }

    public void showVotes() {
        for (Candidato candidato : candidatos.values()) {
            System.out.println(candidato + " " + candidato.getVotos());
        }
    }

    private String candidatosToXml() {
        List<Candidato> candidatosList = new ArrayList<>(candidatos.values());

        try {
            XmlMapper xmlMapper = new XmlMapper();
            String xml = xmlMapper.writeValueAsString(candidatosList);
            return xml;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao fazer o XML");
            return null;
        }
    }

    private List<Candidato> XMLToArray(String xml) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            List<Candidato> candidatos = xmlMapper.readValue(xml, new TypeReference<List<Candidato>>(){});
            return candidatos;
        } catch (Exception e) {
            System.out.println("Erro ao transformar XML para List<Candidato>");
            return null;
        }
    }
}
