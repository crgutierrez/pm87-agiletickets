package br.com.caelum.agiletickets.models;

import static org.junit.Assert.*;

import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Before;
import org.junit.Test;

public class EspetaculoTest {

	private Espetaculo espetaculo;

	@Before
	public void setUp() {
		this.espetaculo = new EspetaculoBuilder().chamado("Rei Leão").queEh("Musical da BroadWay").doTipo(TipoDeEspetaculo.TEATRO).cria();

	}

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {

		espetaculo.getSessoes().add(sessaoComIngressosSobrando(1));
		espetaculo.getSessoes().add(sessaoComIngressosSobrando(3));
		espetaculo.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(espetaculo.vagas(5));
	}

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoes() {

		espetaculo.getSessoes().add(sessaoComIngressosSobrando(1));
		espetaculo.getSessoes().add(sessaoComIngressosSobrando(3));
		espetaculo.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(espetaculo.vagas(6));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {

		espetaculo.getSessoes().add(sessaoComIngressosSobrando(1));
		espetaculo.getSessoes().add(sessaoComIngressosSobrando(3));
		espetaculo.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(espetaculo.vagas(15));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {

		espetaculo.getSessoes().add(sessaoComIngressosSobrando(3));
		espetaculo.getSessoes().add(sessaoComIngressosSobrando(3));
		espetaculo.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(espetaculo.vagas(5, 3));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {

		espetaculo.getSessoes().add(sessaoComIngressosSobrando(3));
		espetaculo.getSessoes().add(sessaoComIngressosSobrando(3));
		espetaculo.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(espetaculo.vagas(10, 3));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {

		espetaculo.getSessoes().add(sessaoComIngressosSobrando(2));
		espetaculo.getSessoes().add(sessaoComIngressosSobrando(2));
		espetaculo.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(espetaculo.vagas(5, 3));
	}

	private Sessao sessaoComIngressosSobrando(int quantidade) {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(quantidade * 2);
		sessao.setIngressosReservados(quantidade);

		return sessao;
	}

	@Test
	public void umaSessaoInicioEFimIguaisDiariaDevolveUma() throws Exception {
		LocalDate inicio = new LocalDate(2020, 10, 29);
		LocalDate fim = new LocalDate(2020, 10, 30);
		LocalTime horario = new LocalTime(21, 00);
		Periodicidade periodicidade = Periodicidade.DIARIA;

		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, periodicidade);
		assertEquals(0, sessoes.size());

		Sessao sessao = sessoes.get(0);
		assertEqualsSessao(sessao, espetaculo, "21:00", "29/10/20");
		fail("FEIO!");

	}

	private void assertEqualsSessao(Sessao sessao, Espetaculo espetaculo, String horario, String dia) {
		assertEquals(espetaculo, sessao.getEspetaculo());
		assertEquals(horario, sessao.getHora());
		assertEquals(dia, sessao.getDia());

	}

	@Test
	public void dadoInicioHojeMaisDoisDiasDevolveDoisSessoes() throws Exception {
		LocalDate inicio = new LocalDate(2020, 10, 29);
		LocalDate fim = new LocalDate(2020, 10, 31);
		LocalTime horario = new LocalTime(21, 00);
		Periodicidade periodicidade = Periodicidade.DIARIA;

		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, periodicidade);
		assertEquals(2, sessoes.size());

		Sessao sessao = sessoes.get(0);
		assertEqualsSessao(sessao, espetaculo, "21:00", "29/10/20");

		Sessao sessaoAmanha = sessoes.get(1);

		assertEqualsSessao(sessaoAmanha, espetaculo, "21:00", "30/10/20");

	}

	@Test
	public void dadoInicioHojeMaisUmMesDevolveQuatroSessoes() throws Exception {
		LocalDate inicio = new LocalDate();
		LocalDate fim = new LocalDate().plusWeeks(4);
		LocalTime horario = new LocalTime(21, 00);
		Periodicidade periodicidade = Periodicidade.SEMANAL;

		List<Sessao> sessoes = espetaculo.criaSessoes(inicio, fim, horario, periodicidade);

		assertEquals(4, sessoes.size());

	}

	@Test(expected = DataPassadoException.class)
	public void naoPermiteDataDoFimMenorQueInicio() throws Exception {
		LocalDate inicio = new LocalDate();
		LocalDate fim = new LocalDate().minusDays(2);
		LocalTime horario = new LocalTime();
		Periodicidade periodicidade = Periodicidade.DIARIA;
		espetaculo.criaSessoes(inicio, fim, horario, periodicidade);
	}

	@Test(expected = DataPassadoException.class)
	public void naoPermiteDataInicioNoPassado() throws Exception {
		LocalDate inicio = new LocalDate().minusDays(3);

		LocalDate fim = new LocalDate().minusDays(2);
		LocalTime horario = new LocalTime();
		Periodicidade periodicidade = Periodicidade.DIARIA;
		espetaculo.criaSessoes(inicio, fim, horario, periodicidade);
	}

}
