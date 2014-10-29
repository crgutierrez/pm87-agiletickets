package br.com.caelum.agiletickets.models;

import static org.junit.Assert.*;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Test;

import br.com.caelum.agiletickets.domain.precos.SessaoTestDataBuilder;

public class EspetaculoTest {

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.vagas(5));
	}

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.vagas(6));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.vagas(15));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.vagas(5, 3));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.vagas(10, 3));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.vagas(5, 3));
	}

	private Sessao sessaoComIngressosSobrando(int quantidade) {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(quantidade * 2);
		sessao.setIngressosReservados(quantidade);

		return sessao;
	}

	@Test
	public void umaSessaoInicioEFimIguaisDiariaDevolveUma() throws Exception {
		Espetaculo espetaculo = new Espetaculo();
		LocalDate inicio = new LocalDate(2014, 10, 29);
		LocalDate fim = new LocalDate(2014, 10, 30);
		LocalTime horario = new LocalTime(21, 00);
		Periodicidade periodicidade = Periodicidade.DIARIA;

		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, periodicidade);
		assertEquals(1, sessoes.size());

		Sessao sessao = sessoes.get(0);
		assertEqualsSessao(sessao,espetaculo,"21:00","29/10/14");
	
	}

	private void assertEqualsSessao(Sessao sessao, Espetaculo espetaculo, String horario, String dia) {
		assertEquals(espetaculo, sessao.getEspetaculo());
		assertEquals(horario, sessao.getHora());
		assertEquals(dia, sessao.getDia());
		
		
		
	}

	@Test
	public void dadoInicioHojeMaisDoisDiasDevolveDoisSessoes() throws Exception {
		Espetaculo espetaculo = new Espetaculo();
		LocalDate inicio = new LocalDate(2014, 10, 29);
		LocalDate fim = new LocalDate(2014, 10, 31);
		LocalTime horario = new LocalTime(21, 00);
		Periodicidade periodicidade = Periodicidade.DIARIA;

		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, periodicidade);
		assertEquals(2, sessoes.size());

		Sessao sessao = sessoes.get(0);
		assertEqualsSessao(sessao,espetaculo,"21:00","29/10/14");
		
		Sessao sessaoAmanha = sessoes.get(1);
		
		assertEqualsSessao(sessaoAmanha,espetaculo,"21:00","30/10/14");
		
	}

	@Test
	public void dadoInicioHojeMaisUmMesDevolveQuatroSessoes() throws Exception {
		Espetaculo espetaculo = new Espetaculo();
		LocalDate inicio = new LocalDate();
		LocalDate fim = new LocalDate().plusWeeks(4);
		LocalTime horario = new LocalTime(21,00);
		Periodicidade periodicidade = Periodicidade.SEMANAL;

		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, periodicidade);
		
		assertEquals(4, sessoes.size());
		
		
		
	}

	@Test(expected = DataPassadoException.class)
	public void naoPermiteDataDoFimMenorQueInicio() throws Exception {
		Espetaculo espetaculo = new Espetaculo();
		LocalDate inicio = new LocalDate();
		LocalDate fim = new LocalDate().minusDays(2);
		LocalTime horario = new LocalTime();
		Periodicidade periodicidade = Periodicidade.DIARIA;
		espetaculo.criaSessoes(inicio, fim, horario, periodicidade);
	}

	@Test(expected = DataPassadoException.class)
	public void naoPermiteDataInicioNoPassado() throws Exception {
		Espetaculo espetaculo = new Espetaculo();
		LocalDate inicio = new LocalDate().minusDays(3);

		LocalDate fim = new LocalDate().minusDays(2);
		LocalTime horario = new LocalTime();
		Periodicidade periodicidade = Periodicidade.DIARIA;
		espetaculo.criaSessoes(inicio, fim, horario, periodicidade);
	}

}
