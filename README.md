
[![Build Status](https://api.travis-ci.org/ppamorim/Ghibli.svg?branch=master)](https://travis-ci.org/ppamorim/Ghibli)

# Configurações

  É necessário instalar as ultimas versões das SDKs do Android (23.0.2) e o app suporta no mínimo a versão 10 da SDK do Android. É recomendável abrir o projeto no Android Studio de ultima versão.
  Não se esqueça de ir em Build Variants e configurar o Test Artifact para Unit Tests

# Estrutura

Neste projeto foi utilizado o padrão MVP (Model-View-Presenter) e injeção de dependências utilizando o pré processador Dagger 2.
Mantive a mínima quantidade possível de bibliotecas no projeto, no entanto, utilizei as melhores e mais atualizadas.

# Bibliotecas

 - Appcompat: Provém de bibliotecas padrões de suporte(Toolbar, ViewCompat...)
 - suport-design: Fornece views Material Design
 - support-annotations: Conector para testes unitários
 - Dagger 2: Injeção de dependências
 - ButterKnife: Inject de view menos verbosa
 - Square OkHttp: Requests Http
 - jUnit: Testes unitários
 - Mockito: Gerador de mock

# Misc

O projeto utiliza o estilo de código da Square, sendo 2 espaços para as indentações.

No momento é o mais recomendado para Android pois facilita a leitura e organização do código (mais compacto).

Travis CI é utilizado para analisar o código e verificar se há alguma divergência do estilo de código.
